/* =========================================================================

    UITypeTable.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import java.io.PrintStream
import java.util.Arrays
import java.util.Comparator
import java.util.Enumeration
import java.util.Hashtable


/**
 * UITypeのシンボルテーブル
 */
class UITypeTable
{

    protected var index : Int = 0
    protected var table = Hashtable<String, UIType>(64)

    val comparatorById : Comparator<UIType> = Comparator { o1, o2 ->
        o1.index - o2.index
    }

    val comparatorByName : Comparator<UIType> = Comparator { o1, o2 ->
        o1.name.compareTo(o2.name)
    }

    enum class SortType
    {
        BY_ID,
        BY_NAME
    }

    /**
     * ctor
     */
    init
    {
        this.index = 0
    }

    /**
     * テーブルへの追加
     */
    fun add(v : UIType) : Boolean
    {
        val name = v.name
        if(table.containsKey(name))
        {
            // 定義済み
            return false
        }

        v.index = index
        index++

        table[name] = v
        return true
    }

    /**
     * 指定したシンボル名がテーブルに登録されているか検索する
     * @return あった場合は有効なインスタンス、無い場合は null
     */
    fun search(name : String) : UIType?
    {
        return table[name]
    }

    /**
     * 指定したシンボル名がテーブルに登録されているか検索する
     * @return あった場合はインデックス番号、無い場合は -1
     */
    fun searchID(name : String) : Int
    {
        val v = search(name) ?: return -1
        return v.index
    }

    /**
     * 登録されているシンボルを配列形式で返す
     */
    private fun toArray(sortType : SortType) : Array<UIType>
    {
        var c : Comparator<UIType>? = when(sortType)
        {
            SortType.BY_ID   -> comparatorById
            SortType.BY_NAME -> comparatorByName
        }

        val array = table.values.toTypedArray()
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
            buff.append(v.name).append('\n')
        }

        return buff.toString()
    }

    /**
     * デバッグ用のダンプ
     */
    fun dumpSymbol(ps : PrintStream)
    {
        for(v in toArray(SortType.BY_NAME))
        {
            ps.println(v.name)
        }
    }

}
