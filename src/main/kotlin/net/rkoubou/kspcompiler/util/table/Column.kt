/* =========================================================================

    Column.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.util.table

/**
 * 列を表現するオブジェクト
 */
class Column
/**
 * ctor.
 */
    (
    /** 格納する値  */
    val value: Any
) {

    /**
     * 表現可能な値の場合に有効な値を返す。表現できない場合は null を返す。
     */
    fun intValue(): Int? {
        try {
            return Integer.parseInt(value.toString())
        } catch (e: Throwable) {
            return null
        }

    }

    /**
     * 表現可能な値の場合に有効な値を返す。表現できない場合は null を返す。
     */
    fun longValue(): Long? {
        try {
            return java.lang.Long.parseLong(value.toString())
        } catch (e: Throwable) {
            return null
        }

    }

    /**
     * 表現可能な値の場合に有効な値を返す。表現できない場合は null を返す。
     */
    fun booleanValue(): Boolean? {
        try {
            return java.lang.Boolean.parseBoolean(value.toString())
        } catch (e: Throwable) {
            return null
        }

    }

    /**
     * value.toString() を返す
     */
    fun stringValue(): String {
        return value.toString()
    }
}
