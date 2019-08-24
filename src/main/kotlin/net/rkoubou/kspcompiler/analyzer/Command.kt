/* =========================================================================

    Command.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import java.util.ArrayList
import java.util.HashMap

import net.rkoubou.kspcompiler.javacc.generated.ASTCallCommand
import net.rkoubou.kspcompiler.javacc.generated.KSPParserTreeConstants

/**
 * コマンドの中間表現を示す
 */
class Command( val astNode : ASTCallCommand )
    : SymbolDefinition(astNode.symbol), KSPParserTreeConstants
{

    /** 引数リスト  */
    val argList = ArrayList<CommandArgument>()

    /** 引数の括弧の有無の判定フラグ  */
    var hasParenthesis : Boolean = false

    /** 戻り値型  */
    val returnType = ReturnType()

    /**
     * 使用可能なコマンドのコールバック（外部低ファイルから読み込むビルトインコマンド用）
     *
     * 詳細： https://github.com/r-koubou/KSPSyntaxParser/wiki/External-symbol-definition-file-format#command
     */
    val availableCallbackList = HashMap<String, Callback?>(32)

    init
    {
        this.astNode.symbol.symbolType = SymbolDefinition.SymbolType.Command
    }

    /**
     * ドキュメント化されていないコマンドで引数リストが不明なコマンドかどうか
     */
    fun unknownCommand() : Boolean
    {
        for(t in this.returnType.typeList)
        {
            if(t == AnalyzerConstants.TYPE_UNKOWN)
            {
                return true
            }
        }
        return false
    }

}
