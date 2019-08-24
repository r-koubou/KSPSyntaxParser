/* =========================================================================

    AnalyzerConstants.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import java.util.regex.Pattern

/**
 * シンボル収集、意味解析フェーズ中に使用する共通の定数
 */
interface AnalyzerConstants
{

    /**
     * コンパイル時に使用する、代入演算子の識別子。
     */
    enum class AssignOprator
    {
        /** 初期値  */
        NULL,

        /** :=  */
        ASSIGN
    }

    /*
     * 変数の状態を示す識別子。
     */
    enum class SymbolState
    {
        /** 未初期化  */
        UNLOADED,

        /** 代入中  */
        LOADING,

        /** 代入済み  */
        LOADED,

        /** 初期化済み  */
        INITIALIZED
    }

    //-------------------------------------------------------------------------------
    // コールバック・ユーザー定義関数識別
    //-------------------------------------------------------------------------------
    enum class FunctionType
    {
        CALLBACK,
        USER_FUNCTION
    }

    companion object
    {

        /** アトリビュート：なし  */
        const val ACCESS_ATTR_NONE = 0x00

        /** アトリビュート：読み取り専用  */
        const val ACCESS_ATTR_CONST = 0x01

        /** アトリビュート：ポリフォニック変数  */
        const val ACCESS_ATTR_POLY = 0x02

        /** アトリビュート：UI変数  */
        const val ACCESS_ATTR_UI = 0x04

        /** アトリビュート：コールバック  */
        const val ACCESS_ATTR_CALLBACK = 0x100

        /** アトリビュート：ユーザー定義関数  */
        const val ACCESS_ATTR_USER_FUNCTION = 0x200

        // 型情報は32bitで構成
/*
    0b 00000000 00000000 00000000 00000000
       ----+--- ---------+----------------
           |             |
           |             +-- bit 0-23 データ型
           |
           +---------------- bit 24-31 配列等の補足情報
*/
        const val TYPE_BIT_SIZE = 24
        const val TYPE_ATTR_BIT_SIZE = 8

        //--------------------------------------------------------------------------
        // 基本型情報 (0x00000000-0x00ffffff)
        //--------------------------------------------------------------------------
        const val TYPE_MASK = 0x00ffffff
        const val TYPE_NONE = 0x00
        const val TYPE_INT = 0x01
        const val TYPE_STRING = 0x02
        const val TYPE_REAL = 0x04
        const val TYPE_BOOL = 0x100
        const val TYPE_VOID = 0x200
        const val TYPE_PREPROCESSOR_SYMBOL = 0x400
        const val TYPE_KEYID = 0x800    // e.g. PGS <key-id>
        const val TYPE_UNKOWN = 0x800000 // If undocumented command arguments

        const val TYPE_NUMERICAL = TYPE_INT or TYPE_REAL
        const val TYPE_NON_VARIABLE = TYPE_PREPROCESSOR_SYMBOL or TYPE_KEYID
        const val TYPE_MULTIPLE = 0xffffff and TYPE_UNKOWN.inv() // 型識別全ビット ON

        //--------------------------------------------------------------------------
        // 配列などの情報フラグ (0x01000000~0xff000000)
        //--------------------------------------------------------------------------
        const val TYPE_ATTR_MASK = -0x1000000
        // 配列
        const val TYPE_ATTR_ARRAY = 0x01000000
        // e.g. type is int[]
        // type = TYPE_INT | TYPE_ATTR_ARRAY
        // if( ( type & TYPE_ATTR_MASK ) == TYPE_ATTR_ARRAY )
        // {
        //     // type is int[]
        // }

        // 全情報ビットフラグON
        const val TYPE_ATTR_ANY = 0x7F000000
        const val TYPE_ALL = -0x1

        //--------------------------------------------------------------------------
        // 上限・下限
        // 32bit signed
        //--------------------------------------------------------------------------

        /** 整数値の下限  */
        const val KSP_INT_MIN = Int.MIN_VALUE
        /** 整数値の上限  */
        const val KSP_INT_MAX = Int.MAX_VALUE

        /** 浮動小数値の下限  */
        const val KSP_REAL_MIN = java.lang.Float.MIN_VALUE
        /** 浮動小数値の上限  */
        const val KSP_REAL_MAX = java.lang.Float.MAX_VALUE

        //-------------------------------------------------------------------------------
        // コンスタントプール
        //-------------------------------------------------------------------------------

        /** コンスタントプール・タグ情報：整数  */
        const val CONSTAT_TAG_INT = 0

        /** コンスタントプール・タグ情報：文字列  */
        const val CONSTAT_TAG_STRING = 1

        //--------------------------------------------------------------------------
        // シンボル名検証
        //--------------------------------------------------------------------------

        /** 変数名の1文字目に数字文字が含まれているかどうか。KSPは許容するが、一般的な言語ではNG  */
        const val REGEX_NUMERIC_PREFIX = "^.[0-9]"

        /** 変数名：データ型記号付いているシンボルの正規表現  */
        const val REGEX_TYPE_PREFIX = "^[\\$|\\%|\\@|\\!\\?|\\~]"

        /** 変数名：データ型記号がつかないプリプロセッサシンボル等のシンボルの正規表現  */
        const val REGEX_NON_TYPE_PREFIX = "^[a-z|A-Z|_]"

        /**
         * NI が使用を禁止している変数名の接頭文字
         */
        val RESERVED_VARIABLE_PREFIX_LIST = arrayOf(
            // From KSP Reference Manual:
            // Please do not create variables with the prefixes below, as these prefixes are used for
            // internal variables and constants
            "\$NI_", "\$CONTROL_PAR_", "\$EVENT_PAR_", "\$ENGINE_PAR_"
        )
    }
}
