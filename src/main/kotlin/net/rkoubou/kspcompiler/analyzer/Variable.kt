/* =========================================================================

    Variable.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import net.rkoubou.kspcompiler.javacc.generated.ASTVariableDeclaration

/**
 * KSPの値、変数の中間表現を示す
 */
open class Variable( val astNode : ASTVariableDeclaration ) : SymbolDefinition()
{

    /** 配列型の場合の要素数  */
    var arraySize = 0
    /**
     * UI型変数の場合に値がセットされる（シンボル収集フェーズ）
     * 初期値は null
     * @see SymbolCollector
     */
    var uiTypeInfo : UIType? = null

    /** コンスタントプールに格納される場合のインデックス番号  */
    var constantIndex = -1

    /** on init 内で使用可能な変数かどうか（外部低ファイルから読み込むビルトイン変数用）  */
    var availableOnInit = true

    /** 単項演算子により生成、かつリテラル値  */
    var constantValueWithSingleOperator = false

    /**
     * 変数の型データからKSP文法の変数名表現に変換する
     */
    // 1文字目に型情報の文字を含んでいる場合はそのまま返す
    val variableName : String
        get() = if(Regex(AnalyzerConstants.REGEX_NUMERIC_PREFIX).find(name) != null)
        {
            name
        }
        else toKSPTypeCharacter() + name

    init
    {
        SymbolDefinition.copy(astNode.symbol, this)
        this.symbolType = SymbolDefinition.SymbolType.Variable
    }

    /**
     * 変数の型データからKSP文法の変数名表現に変換する
     */
    override fun toString() : String
    {
        return variableName
    }
}
