/* =========================================================================

    Position.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import net.rkoubou.kspcompiler.javacc.generated.Token

/**
 * シンボル等の行番号、列を格納する
 */
class Position
{
    var beginLine = 0
    var endLine = 0
    var beginColumn = 0
    var endColumn = 0

    /**
     * ctor.
     */
    constructor()

    /**
     * コピーコンストラクタ
     */
    constructor(src : Position)
    {
        copy(src, this)
    }

    /**
     * ディープコピー
     */
    fun copy(src : Position)
    {
        this.beginLine = src.beginLine
        this.endLine = src.endLine
        this.beginColumn = src.beginColumn
        this.endColumn = src.endColumn
    }

    /**
     * Tokenからの値のディープコピー
     */
    fun copy(src : Token)
    {
        this.beginLine = src.beginLine
        this.endLine = src.endLine
        this.beginColumn = src.beginColumn
        this.endColumn = src.endColumn
    }

    /**
     * 行数カウント
     */
    fun lineCount() : Int
    {
        return endLine - beginLine + 1
    }

    /**
     * 列数カウント
     */
    fun columnCount() : Int
    {
        return endColumn - beginColumn + 1
    }

    companion object
    {

        /**
         * ディープコピー
         */
        fun copy(src : Position, dest : Position)
        {
            dest.beginLine = src.beginLine
            dest.endLine = src.endLine
            dest.beginColumn = src.beginColumn
            dest.endColumn = src.endColumn
        }

        /**
         * Tokenからの値のディープコピー
         */
        fun copy(src : Token, dest : Position)
        {
            dest.beginLine = src.beginLine
            dest.endLine = src.endLine
            dest.beginColumn = src.beginColumn
            dest.endColumn = src.endColumn
        }

        /**
         * TokenからPositionを生成するコンビニエンスメソッド
         */
        fun create(t : Token) : Position
        {
            val p = Position()
            p.beginLine = t.beginLine
            p.endLine = t.endLine
            p.beginColumn = t.beginColumn
            p.endColumn = t.endColumn
            return p
        }
    }
}