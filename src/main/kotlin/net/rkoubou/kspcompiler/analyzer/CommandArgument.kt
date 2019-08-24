/* =========================================================================

    CommandArgument.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import java.util.ArrayList

/**
 * コマンドコール時：引数の変数の中間表現を示す
 */
class CommandArgument : AnalyzerConstants
{

    /** 引数を格納する（複数タイプを許容するコマンドに対応するため配列としている）  */
    val arguments = ArrayList<Argument>()

    /**
     * 現在の許容する引数のデータ型の数を取得する。
     */
    val typeNum : Int
        get() = arguments.size

    /**
     * Ctor.
     */
    constructor(vararg args : Variable)
    {
        for(a in args)
        {
            this.add(Argument(a))
        }
    }

    /**
     * Ctor.
     */
    constructor(args : ArrayList<Variable>)
    {
        for(a in args)
        {
            this.add(Argument(a))
        }
    }

    /**
     * Ctor.
     */
    constructor(vararg args : Argument)
    {
        for(a in args)
        {
            this.add(a)
        }
    }

    /**
     * 引数に複数のデータ型を許容する場合は、このメソッドにて追加をする。
     */
    fun add(arg : Argument)
    {
        arg.name = "arg"
        arg.requireDeclarationOnInit = false   // ビルトイン変数も有効なのでフラグは下ろす
        arguments.add(arg)
    }

    /**
     * 引数に複数のデータ型を許容する場合は、このメソッドにて追加をする。
     */
    fun add(args : Array<Argument>)
    {
        for(a in args)
        {
            this.add(a)
        }
    }

    /**
     * 現在の引数情報を取得する。
     */
    fun get() : ArrayList<Argument>
    {
        return ArrayList(arguments)
    }
}
