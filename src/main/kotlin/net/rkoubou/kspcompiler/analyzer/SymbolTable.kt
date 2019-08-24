/* =========================================================================

    SymbolTable.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import java.io.PrintStream
import java.util.Arrays
import java.util.Comparator
import java.util.Enumeration
import java.util.Hashtable

import net.rkoubou.kspcompiler.javacc.generated.SimpleNode
import net.rkoubou.kspcompiler.obfuscator.ShortSymbolGenerator


/**
 * ASTをベースにした基底シンボルテーブル
 */
abstract class SymbolTable<NODE : SimpleNode, SYMBOL : SymbolDefinition> : AnalyzerConstants
{

    /** ネストなどのローカルスコープを許容する場合に使用する親ノード  */
    var parent : SymbolTable<NODE, SYMBOL>? = null
        protected set

    /** シンボルに割り当てられるユニークなインデックス値  */
    protected var index : Int = 0

    /** テーブル本体  */
    protected var table = Hashtable<String, SYMBOL>(512)

    /** ソート用比較処理（インデックス）  */
    val comparatorByIndex : Comparator<SymbolDefinition> = Comparator { o1, o2 ->
        /**
         * @see java.util.Comparator.compare
         */
        /**
         * @see java.util.Comparator.compare
         */
        o1.index - o2.index
    }

    /** ソート用比較処理（シンボルタイプ）  */
    val comparatorByType : Comparator<SymbolDefinition> = Comparator { o1, o2 ->
        val cmp = o1.symbolType.compareTo(o2.symbolType)
        if(cmp == 0)
        {
            comparatorByIndex.compare(o1, o2)
        }
        else cmp
    }

    /**
     * ソートタイプ
     */
    enum class SortType
    {
        /** テーブルインデックス値  */
        BY_INDEX,
        /** シンボルタイプ  */
        BY_TYPE
    }

    /**
     * ctor
     */
    constructor()
    {
        this.index = 0
        parent = null
    }

    /**
     * ctor
     */
    constructor(parent : SymbolTable<NODE, SYMBOL>)
    {
        this.index = parent.index
        this.parent = parent
    }

    /**
     * ctor
     */
    constructor(parent : SymbolTable<NODE, SYMBOL>, startIndex : Int)
    {
        this.index = startIndex
        this.parent = parent
    }

    /**
     * シンボルテーブルへの追加
     */
    abstract fun add(node : NODE) : Boolean

    /**
     * 指定したシンボル名がテーブルに登録されているか検索する
     * @return あった場合は有効なインスタンス、無い場合は null
     */
    @JvmOverloads
    fun search(name : String?, enableSearchParent : Boolean = true) : SYMBOL?
    {
        var v : SYMBOL? = table[name!!]
        if(v == null && enableSearchParent)
        {
            var p = parent
            while(p != null)
            {
                v = p.table[name]
                if(v != null)
                {
                    return v
                }
                p = p.parent
            }
            return null
        }
        return v
    }

    /**
     * 指定したシンボルがテーブルに登録されているか検索する
     * @return あった場合はsymbol自身、無い場合は null
     */
    fun search(symbol : SymbolDefinition, enableSearchParent : Boolean) : SYMBOL?
    {
        return search(symbol.getName(true), enableSearchParent)
    }

    /**
     * 指定したシンボルがテーブルに登録されているか検索する
     * @return あった場合はsymbol自身、無い場合は null
     */
    fun search(symbol : SymbolDefinition) : SYMBOL?
    {
        return search(symbol.getName(true), true)
    }

    /**
     * 指定したインデックス値でテーブルに登録されているか検索する
     * @return あった場合はsymbol自身、無い場合は null
     */
    fun search(index : Int) : SYMBOL?
    {
        val e = table.elements()
        while(e.hasMoreElements())
        {
            val v = e.nextElement()
            if(v.index == index)
            {
                return v
            }
        }
        return null
    }

    /**
     * 指定したシンボル名がテーブルに登録されているか検索する
     * @return あった場合はインデックス番号、無い場合は -1
     */
    @JvmOverloads
    fun searchID(name : String?, enableSearchParent : Boolean = true) : Int
    {
        var v = search(name, enableSearchParent)
        if(v == null)
        {
            var p = parent
            while(p != null)
            {
                v = p.table[name!!]
                if(v != null)
                {
                    return v.index
                }
                p = p.parent
            }
            return -1
        }
        return v.index
    }

    /**
     * 指定したシンボルがテーブルに登録されているか検索する
     * @return あった場合はインデックス番号、無い場合は -1
     */
    fun searchID(symbol : SymbolDefinition, enableSearchParent : Boolean) : Int
    {
        return searchID(symbol.getName(true), enableSearchParent)
    }

    /**
     * 指定したシンボルがテーブルに登録されているか検索する
     * @return あった場合はインデックス番号、無い場合は -1
     */
    fun searchID(symbol : SymbolDefinition) : Int
    {
        return searchID(symbol.getName(true))
    }

    /**
     * 登録されているシンボルを配列形式で返す
     */
    fun toArray() : Array<SymbolDefinition>
    {
        return table.values.toTypedArray()
    }

    /**
     * 登録されているシンボルを配列形式で返す
     */
    private fun toArray(sortType : SortType) : Array<SymbolDefinition>
    {
        var c : Comparator<SymbolDefinition>? = when(sortType)
        {
            SortType.BY_INDEX -> comparatorByIndex
            SortType.BY_TYPE  -> comparatorByType
        }

        val array = table.values.toTypedArray<SymbolDefinition>()
        if(array.isNotEmpty())
        {
            Arrays.sort(array, c)
        }

        return array

    }

    /**
     * @see java.lang.Object.toString
     */
    override fun toString() : String
    {
        val buff = StringBuilder(64)
        val e = table.elements()
        while(e.hasMoreElements())
        {
            val v = e.nextElement()
            buff.append(v.toString()).append('\n')
        }

        return buff.toString()
    }

    /**
     * デバッグ用のダンプ
     */
    fun dumpSymbol(ps : PrintStream)
    {
        for(v in toArray(SortType.BY_TYPE))
        {
            ps.println(v.name)
        }
    }

    /**
     * シンボル名のオブファスケートを行う
     */
    fun obfuscate()
    {
        for(v in table.elements())
        {
            if(v.name.isNotEmpty() && !v.reserved)
            {
                v.obfuscatedName = ShortSymbolGenerator.generate(v.name)
            }
        }
    }
}
