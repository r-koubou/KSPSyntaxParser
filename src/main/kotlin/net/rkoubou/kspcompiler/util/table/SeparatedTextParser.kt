/* =========================================================================

    SeparatedTextParser.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.util.table

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.ArrayList
import java.util.regex.Pattern

import net.rkoubou.kspcompiler.util.StreamCloser
import kotlin.math.max

/**
 * 区切り文字を用いたテキストファイルをパース、行毎の列データを保有する
 */
abstract class SeparatedTextParser
{

    /** パース対象の文字コード（デフォルトではUTF-8）  */
    private val delimiter : String

    /** パース対象の文字コード（デフォルトではUTF-8）  */
    private val encoding : String

    /** 最大列数  */
    private var maxColumnsNum : Int = 0

    /** 行・列データTを格納する  */
    val table = ArrayList<Row>(256)

    /**
     * ctor.
     */
    constructor()
    {
        encoding = "utf-8"
        delimiter = DEFAULT_DELIMITER
    }

    /**
     * ctor.
     */
    constructor(encoding : String, delimiterRegex : String)
    {
        this.encoding = encoding
        this.delimiter = delimiterRegex
    }

    /**
     * 指定されたパスからパースを実行する
     */
    @Throws(IOException::class)
    fun parse(path : String)
    {
        parse(File(path))
    }

    /**
     * 指定されたパスからパースを実行する
     */
    @Throws(IOException::class)
    fun parse(file : File)
    {
        parse(FileInputStream(file))
    }

    /**
     * 指定された入力ストリームからパースを実行する
     */
    @Throws(IOException::class)
    fun parse(stream : InputStream)
    {
        BufferedReader(InputStreamReader(stream, encoding)).use {
            while(true)
            {
                var line = it.readLine() ?: break
                val skip : Boolean = Regex(REGEX_LINECOMMENT).find(line) != null

                line = line.trim()
                if(skip)
                {
                    continue
                }

                val split : Array<String> = line.split(delimiter.toRegex()).dropLastWhile { line.isEmpty() }.toTypedArray()
                table.add(parseLine(line, split))
                max(maxColumnsNum, split.size)
            }
        }
    }

    /**
     * パース前の初期値にリセットする
     */
    fun clear()
    {
        table.clear()
        maxColumnsNum = 0
    }

    /** 指定された行の列全体を取得する  */
    fun getRow(rowIndex : Int) : ArrayList<Column>
    {
        val r = table[rowIndex]
        return ArrayList(r.columns)
    }

    /**
     * [.parse] から行を読み込む毎に呼び出される。
     * @return 行をパースした結果
     */
    protected abstract fun parseLine(line : String, split : Array<String>) : Row

    companion object
    {
        /** 初期設定のデリミタ(TAB)  */
        const val DEFAULT_DELIMITER = "\t"

        /** 行コメント表現(正規表現)  */
        const val REGEX_LINECOMMENT = "^\\s*#"
    }
}
