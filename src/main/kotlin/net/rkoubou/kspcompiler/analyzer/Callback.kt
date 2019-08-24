/* =========================================================================

    Callback.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import java.util.ArrayList

import net.rkoubou.kspcompiler.javacc.generated.ASTCallbackArgumentList
import net.rkoubou.kspcompiler.javacc.generated.ASTCallbackDeclaration
import net.rkoubou.kspcompiler.javacc.generated.ASTVariableDeclaration
import net.rkoubou.kspcompiler.javacc.generated.KSPParserTreeConstants

/**
 * コールバック（引数なし）の中間表現を示す
 */
open class Callback : SymbolDefinition, KSPParserTreeConstants
{

    /** 元となるASTノード  */
    private val astNode : ASTCallbackDeclaration

    /** 多重宣言を許可するかどうか(例：on ui_controlなどは複数宣言可能)  */
    private var allowDuplicate : Boolean = false

    /** 外部定義ファイルからの取り込みとの組み合わせで使用する(allowDuplicate)  */
    var declared = false

    /** このコールバックに割り当てられているユニークID  */
    protected var duplicateID : Long? = null

    /** 引数リスト  */
    val argList = ArrayList<Argument>()

    /**
     * このコールバックが多重定義を許容しているかどうか
     */
    /**
     * このコールバックが多重定義を許容するかを設定する。
     * @throws RuntimeException flag が true、かつ isAllowDuplicate() が true を返す条件を満たしている場合
     */
    var isAllowDuplicate : Boolean
        get() = allowDuplicate
        set(flag)
        {
            if(flag)
            {
                if(duplicateID != null)
                {
                    throw RuntimeException("Already set allow duplication flag")
                }
                duplicateID = duplicateCounter
                incDuplicateID()
            }
            else
            {
                duplicateID = null
            }
            allowDuplicate = flag
        }

    /**
     * Ctor.
     */
    constructor(node : ASTCallbackDeclaration)
    {
        SymbolDefinition.copy(node.symbol, this)
        this.astNode = node
        this.symbolType = SymbolDefinition.SymbolType.Callback
        updateArgList()
    }

    /**
     * Ctor.
     */
    constructor(c : Callback)
    {
        astNode = c.astNode
        Callback.copy(c, this)
    }

    /**
     * ASTノードCallbackArgumentListに格納されている変数文字列を元にargListを更新する。
     */
    fun updateArgList()
    {
        if(astNode.jjtGetNumChildren() < 2)
        {
            // 引数リストなし
            argList.clear()
            return
        }
        val list = astNode.jjtGetChild(0) as ASTCallbackArgumentList
        argList.clear()
        for(n in list.args)
        {
            val decl = ASTVariableDeclaration(KSPParserTreeConstants.JJTVARIABLEDECLARATION)
            decl.symbol.symbolType = SymbolDefinition.SymbolType.Variable
            decl.symbol.name = n

            val arg = Argument(decl)
            argList.add(arg)
        }
    }

    /**
     * ユニークIDを付与した内部形式でのシンボル名を返す
     */
    override var name: String = ""
    get()
    {
        return if(allowDuplicate)
        {
            super.name + DUPLICATE_DELIMITER + duplicateID
        }
        else super.name
    }

    companion object
    {
        /** getName() メソッドにて、内部ユニークIDを付与する場合に使用するデリミタ文字  */
        var DUPLICATE_DELIMITER = "<>"
        /** ユニークIDカウンタ  */
        protected var duplicateCounter = 0L

        /**
         * 値コピー
         */
        fun copy(src : Callback, dest : Callback)
        {
            SymbolDefinition.copy(src, dest)
            dest.allowDuplicate = src.allowDuplicate
            dest.declared = src.declared
            dest.argList.clear()
            dest.argList.addAll(src.argList)
            dest.duplicateID = null
            dest.isAllowDuplicate = src.isAllowDuplicate
        }

        /**
         * 内部ユニークIDのインクリメント
         */
        protected fun incDuplicateID()
        {
            duplicateCounter++
            if(duplicateCounter == java.lang.Long.MAX_VALUE)
            {
                throw RuntimeException("No more generate ID!")
            }
        }
    }
}
