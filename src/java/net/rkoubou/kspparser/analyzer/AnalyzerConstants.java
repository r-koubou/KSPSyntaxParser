/* =========================================================================

    AnalyzerConstants.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspparser.analyzer;

/**
 * シンボル収集、意味解析フェーズ中に使用する共通の定数
 */
public interface AnalyzerConstants
{

    /** アトリビュート：読み取り専用 */
    int ACCESS_ATTR_CONST = 0x01;

    /** アトリビュート：ポリフォニック変数 */
    int ACCESS_ATTR_POLY = 0x02;

    /** アトリビュート：UI変数 */
    int ACCESS_ATTR_UI  = 0x04;

    /** アトリビュート：コールバック */
    int ACCESS_ATTR_CALLBACK = 0x100;

    /** アトリビュート：ユーザー定義関数 */
    int ACCESS_ATTR_USER_FUNCTION = 0x200;

    /**
     * コンパイル時に使用する、代入演算子の識別子。
     */
    public enum AssignOprator
    {
        /** 初期値 */
        NULL,

        /** := */
        ASSIGN,
    };

// 型情報は16bitで構成
/*
    0b 00000000 00000000
       ---+---- ---+----
          |        |
          |        +-- データ型
          +----------- 配列等の補足情報
*/
    //--------------------------------------------------------------------------
    // 基本型情報
    //--------------------------------------------------------------------------
    int TYPE_MASK    = 0xff;
    int TYPE_UNKNOWN = 0x00;
    int TYPE_INT     = 0x01;
    int TYPE_STRING  = 0x02;
    int TYPE_REAL    = 0x03;
    //--------------------------------------------------------------------------
    // 配列などの情報フラグ (0x0100~0xff00)
    //--------------------------------------------------------------------------
    int TYPE_ATTR_MASK  = 0xff00;
    int TYPE_ATTR_BASE = ( 1 << 8 );
    // 配列
    int TYPE_ATTR_ARRAY = TYPE_ATTR_BASE + 0x01;
    // e.g. type is int[]
    // type = TYPE_INT | TYPE_ATTR_ARRAY
    // if( ( type & TYPE_ATTR_MASK ) == TYPE_ATTR_ARRAY )
    // {
    //     // type is int[]
    // }

    /*
     * 変数の状態を示す識別子。
     */
    public enum VariableState
    {
        /** 未初期化 */
        UNLOADED,

        /** 代入中 */
        LOADING,

        /** 代入済み */
        LOADED,

        /** 初期化済み */
        INITIALIZED,
    }

    //-------------------------------------------------------------------------------
    // コールバック・ユーザー定義関数識別
    //-------------------------------------------------------------------------------
    int FUNCTION_TYPE_CALLBACK = 1;
    int FUNCTION_TYPE_USER_DEF = 2;

    //-------------------------------------------------------------------------------
    // コンスタントプール
    //-------------------------------------------------------------------------------

    /** コンスタントプール・タグ情報：整数 */
    int CONSTAT_TAG_INT    = 0;

    /** コンスタントプール・タグ情報：文字列 */
    int CONSTAT_TAG_STRING = 1;

}