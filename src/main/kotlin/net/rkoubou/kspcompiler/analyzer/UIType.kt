/* =========================================================================

    UIType.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import java.util.Arrays

/**
 * 変数宣言時：UIの種類を識別するための中間表現を示す
 */
class UIType(
    /** UIの種類 ui_######  */
    val name : String,
    /** 実行環境で予約済みのシンボルかどうか）  */
    val reserved : Boolean,
    /**
     * UI変数の「値を格納するデータ型」
     *
     *
     * ui_knob の場合、変数は $xxxx (int) だが、格納する値は int[] である。
     * その場合、変数の型はこのクラスが持つデータ型が優先される。
     *
     */
    val uiValueType : Int,
    /** 値代入が可能かどうか（定数扱いにするかどうか）  */
    val constant : Boolean = false,
    /** 初期値代入が必須かどうか  */
    val initializerRequired : Boolean, typeList : IntArray?
) : AnalyzerConstants
{

    /** 初期値代入が必要な場合の初期値の型リスト  */
    var initilzerTypeList = IntArray(0)

    /** シンボルテーブルインデックス値  */
    var index = -1

    init
    {
        if(initializerRequired && typeList != null && typeList.isNotEmpty())
        {
            this.initilzerTypeList = Arrays.copyOf(typeList, typeList.size)
        }
    }

    companion object
    {

        /** 初期値代入が不要な場合の大体リスト  */
        val EMPTY_INITIALIZER_TYPE_LIST = IntArray(0)

        /** 全UIタイプを表現するインスタンス。コマンド引数の意味解析時のインスタンス比較用  */
        var ANY_UI = UIType("ui_*", true, AnalyzerConstants.TYPE_MULTIPLE, false, false, IntArray(0))
    }
}
