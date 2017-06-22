/* =========================================================================

    SymbolCollection.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspparser.analyzer;

import net.rkoubou.kspparser.javacc.generated.KSPParserDefaultVisitor;
import net.rkoubou.kspparser.javacc.generated.KSPParserTreeConstants;
import net.rkoubou.kspparser.javacc.generated.Node;
import net.rkoubou.kspparser.javacc.generated.SimpleNode;
import net.rkoubou.kspparser.analyzer.SymbolDefinition.SymbolType;
import net.rkoubou.kspparser.javacc.generated.ASTCallbackDeclaration;
import net.rkoubou.kspparser.javacc.generated.ASTPreProcessorDefine;
import net.rkoubou.kspparser.javacc.generated.ASTPreProcessorUnDefine;
import net.rkoubou.kspparser.javacc.generated.ASTRootNode;
import net.rkoubou.kspparser.javacc.generated.ASTUserFunctionDeclaration;
import net.rkoubou.kspparser.javacc.generated.ASTVariableDeclaration;;

/**
 * シンボルテーブル構築クラス
 */
public class SymbolCollector extends KSPParserDefaultVisitor implements AnalyzerConstants, KSPParserTreeConstants
{
    public final ASTRootNode rootNode;
    public final UITypeTable uiTypeTable               = new UITypeTable();
    public final VariableTable variableTable           = new VariableTable();
    public final CallbackTable callbackTable           = new CallbackTable();
    public final UserFunctionTable userFunctionTable   = new UserFunctionTable();

    /**
     * NI が使用を禁止している変数名の接頭文字
     */
    static private final String[] RESERVED_VARIABLE_PREFIX_LIST =
    {
        // From KSP Reference Manual:
        // Please do not create variables with the prefixes below, as these prefixes are used for
        // internal variables and constants
        "$NI_",
        "$CONTROL_PAR_",
        "$EVENT_PAR_",
        "$ENGINE_PAR_",
    };

    /**
     * ctor.
     */
    public SymbolCollector( ASTRootNode node )
    {
        this.rootNode = node;
    }

    /**
     * 予約済み変数を定義ファイルから収集する
     */
    private void collectReservedariable()
    {
        ReservedSymbolManager mgr = ReservedSymbolManager.getManager();
        try
        {
            mgr.load();
            mgr.apply( uiTypeTable );
            mgr.apply( variableTable );
            mgr.apply( callbackTable );
        }
        catch( Throwable e )
        {
            e.printStackTrace();
        }
    }

    /**
     * ユーザースクリプトからシンボルを収集
     */
    public void collect()
    {
        collectReservedariable();
        this.rootNode.jjtAccept( this, null );
    }

    /**
     * 変数テーブル構築
     */
    @Override
    public Object visit( ASTVariableDeclaration node, Object data )
    {
        Object ret = defaultVisit( node, data );
//--------------------------------------------------------------------------
/*
    変数
        [node]
        VariableDeclaration
            -> VariableDeclarator
                -> [VariableInitializer]
                    -> Expression
*/
//--------------------------------------------------------------------------
        if( validateVariableImpl( node ) )
        {
            variableTable.add( node );
            Variable v = variableTable.searchVariable( node.symbol.name );
            //--------------------------------------------------------------------------
            // UI変数チェック / 外部定義とのマージ
            //--------------------------------------------------------------------------
            if( v.isUIVariable() )
            {
                String uiName = v.uiTypeName;
                UIType uiType = uiTypeTable.search( uiName );
                if( uiType == null )
                {
                    // NI が定義していないUIの可能性
                    MessageManager.printlnW( MessageManager.PROPERTY_WARN_UI_VARIABLE_UNKNOWN, v );
                }
                else
                {
                    // UI変数に適したデータ型へマージ
                    v.accessFlag = ACCESS_ATTR_UI;
                    v.type       = uiType.uiValueType;
                    if( uiType.constant )
                    {
                        v.accessFlag |= ACCESS_ATTR_CONST;
                    }
                    // 意味解析フェーズで詳細を参照するため保持
                    v.uiTypeInfo = uiType;
                }
            }
        }

        return ret;
    }

    /**
     * 変数、プリプロセッサシンボル収集の共通の事前検証処理
     */
    protected boolean validateVariableImpl( SimpleNode node )
    {
        //--------------------------------------------------------------------------
        // 変数は on init 内でしか宣言できない
        //--------------------------------------------------------------------------
        ASTCallbackDeclaration currentCallBack = null;
        {
            Node n = node.jjtGetParent();
            do
            {
                if( n.getId() == JJTCALLBACKDECLARATION )
                {
                    currentCallBack = (ASTCallbackDeclaration)n;
                    break;
                }
                n = n.jjtGetParent();
            }while( true );
        }
        //--------------------------------------------------------------------------
        // 変数名の検証（型チェックは意味解析フェーズで実行）
        //--------------------------------------------------------------------------
        {
            SymbolDefinition d = node.symbol;
            //--------------------------------------------------------------------------
            // 予約済み（NIが禁止している）接頭語検査
            //--------------------------------------------------------------------------
            {
                for( String n : RESERVED_VARIABLE_PREFIX_LIST )
                {
                    if( d.name.startsWith( n ) )
                    {
                        MessageManager.printlnE( MessageManager.PROPERTY_ERROR_VARIABLE_PREFIX_RESERVED, d );
                        break;
                    }
                }
            }
            //--------------------------------------------------------------------------
            // on init 外での宣言検査
            //--------------------------------------------------------------------------
            if( !currentCallBack.symbol.name.equals( "init" ) )
            {
                MessageManager.printlnE( MessageManager.PROPERTY_ERROR_VARIABLE_ONINIT, d );
            }
            //--------------------------------------------------------------------------
            // 定義済みの検査
            //--------------------------------------------------------------------------
            {
                Variable v = variableTable.searchVariable( d.name );
                // NI の予約変数との重複
                if( v != null && v.reserved )
                {
                    MessageManager.printlnE( MessageManager.PROPERTY_ERROR_VARIABLE_RESERVED, d );
                    return false;
                }
                // ユーザー変数との重複
                else if( v != null )
                {
                    MessageManager.printlnE( MessageManager.PROPERTY_ERROR_VARIABLE_DECLARED, d );
                    return false;
                }
                // 未定義：新規追加可能
                else
                {
                    return true;
                }
            }
        }
    }

    /**
     * プリプロセッサシンボル定義
     */
    @Override
    public Object visit( ASTPreProcessorDefine node, Object data )
    {
        Object ret = defaultVisit( node, data );
        // プリプロセッサなので、既に宣言済みなら上書きもせずそのまま。
        // 複数回宣言可能な KONTAKT 側の挙動に合わせる形をとった。
        {
            ASTVariableDeclaration decl = new ASTVariableDeclaration( JJTVARIABLEDECLARATION );
            SymbolDefinition.copy( node.symbol,  decl.symbol );
            decl.symbol.symbolType = SymbolType.PreprocessorSymbol;

            Variable v = new Variable( decl );
            variableTable.add( v );
        }
        return ret;
    }

    /**
     * プリプロセッサシンボル破棄
     */
    @Override
    public Object visit( ASTPreProcessorUnDefine node, Object data )
    {
        Object ret = defaultVisit( node, data );
        // 宣言されていないシンボルを undef しようとした場合
        if( variableTable.searchVariable( node.symbol.name ) == null )
        {
            MessageManager.printlnW( MessageManager.PROPERTY_WARN_PREPROCESSOR_UNKNOWN_DEF, node.symbol );
        }
        return ret;
    }

    /**
     * コールバックテーブル構築
     */
    @Override
    public Object visit( ASTCallbackDeclaration node, Object data )
    {
        Object ret = defaultVisit( node, data );

        if( !callbackTable.add( node ) )
        {
            MessageManager.printlnE( MessageManager.PROPERTY_ERROR_CALLBACK_DECLARED, node.symbol );
        }

        return ret;
    }

    /**
     * ユーザー定義関数テーブル構築
     */
    @Override
    public Object visit( ASTUserFunctionDeclaration node, Object data )
    {
        Object ret = defaultVisit( node, data );

        if( !userFunctionTable.add( node ) )
        {
            MessageManager.printlnE( MessageManager.PROPERTY_ERROR_FUNCTION_DECLARED, node.symbol );
        }

        return ret;
    }
}