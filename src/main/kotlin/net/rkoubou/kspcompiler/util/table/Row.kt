/* =========================================================================

    Column.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.util.table

import java.util.ArrayList

/**
 * 行を表現する基底クラス
 */
class Row {
    /** 行データを格納するテーブル  */
    val columns: ArrayList<Column>

    /**
     * ctor.
     */
    constructor() {
        columns = ArrayList()
    }

    /**
     * ctor.
     */
    constructor(src: ArrayList<Column>) {
        columns = ArrayList(src)
    }

    /**
     * 列数を取得する
     */
    fun length(): Int {
        return columns.size
    }

    /**
     * 指定された位置の列のデータを取得する
     */
    fun add(col: Column) {
        columns.add(col)
    }

    /**
     * 指定された位置の列のデータを取得する
     */
    operator fun get(index: Int): Column {
        return columns[index]
    }

    /**
     * 指定された位置の列のデータを取得する
     */
    fun clear() {
        columns.clear()
    }

    /**
     * Colmnインスタンスへのコンビニエンスメソッド
     */
    fun intValue(index: Int): Int? {
        return columns[index].intValue()
    }

    /**
     * Colmnインスタンスへのコンビニエンスメソッド
     */
    fun longValue(index: Int): Long? {
        return columns[index].longValue()
    }

    /**
     * Colmnインスタンスへのコンビニエンスメソッド
     */
    fun booleanValue(index: Int): Boolean? {
        return columns[index].booleanValue()

    }

    /**
     * Colmnインスタンスへのコンビニエンスメソッド
     */
    fun stringValue(index: Int): String {
        return columns[index].stringValue()
    }
}
