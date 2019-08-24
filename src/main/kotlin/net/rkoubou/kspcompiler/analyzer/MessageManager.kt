/* =========================================================================

    MessageManager.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.util.Locale
import java.util.Properties

import net.rkoubou.kspcompiler.ApplicationConstants
import net.rkoubou.kspcompiler.javacc.generated.ParseException
import net.rkoubou.kspcompiler.util.StreamCloser
import kotlin.system.exitProcess

/**
 * パース中処理中のメッセージ処理出力に関するマネージャー。
 * I18Nや変数展開機能を提供する。
 */
object MessageManager
{

    //--------------------------------------------------------------------------
    // Lexical
    //--------------------------------------------------------------------------
    val PROPERTY_ERROR_SYNTAX = "error.syntax"

    //--------------------------------------------------------------------------
    // General
    //--------------------------------------------------------------------------
    val PROPERTY_ERROR_GENERAL_SYMBOL_PREFIX_NUMERIC = "error.general.symbol.prefix.numeric"

    //--------------------------------------------------------------------------
    // Variable
    //--------------------------------------------------------------------------
    val PROPERTY_ERROR_VARIABLE_ONINIT = "error.variable.declared.oninit"
    val PROPERTY_ERROR_VARIABLE_DECLARED = "error.variable.already.declared"
    val PROPERTY_ERROR_VARIABLE_RESERVED = "error.variable.reserved"
    val PROPERTY_ERROR_VARIABLE_PREFIX_RESERVED = "error.variable.reserved.prefix"
    val PROPERTY_WARN_UI_VARIABLE_UNKNOWN = "warning.variable.ui.unknown"

    //--------------------------------------------------------------------------
    // PreProcessor
    //--------------------------------------------------------------------------
    val PROPERTY_WARN_PREPROCESSOR_UNKNOWN_DEF = "warn.preprocessor.unknown.defined"
    val PROPERTY_WARN_PREPROCESSOR_ALREADY_DEF = "warn.preprocessor.already.defined"

    //--------------------------------------------------------------------------
    // Callback
    //--------------------------------------------------------------------------
    val PROPERTY_ERROR_CALLBACK_DECLARED = "error.callback.already.declared"
    val PROPERTY_WARN_CALLBACK_UNKNOWN = "warning.callback.unknown"

    //--------------------------------------------------------------------------
    // Command
    //--------------------------------------------------------------------------
    const val PROPERTY_WARN_COMMAND_UNKNOWN = "warning.command.unknown"

    //--------------------------------------------------------------------------
    // User Function
    //--------------------------------------------------------------------------
    const val PROPERTY_ERROR_FUNCTION_DECLARED = "error.userfunction.already.declared"
    const val PROPERTY_ERROR_USERFUNCTION_NOT_DECLARED = "error.userfunction.not.declared"

    //--------------------------------------------------------------------------
    // Semantic Analyzer
    //--------------------------------------------------------------------------
    const val PROPERTY_ERROR_SEMANTIC_EXPRESSION_INVALID = "error.semantic.expression.invalid"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_NOT_DECLARED = "error.semantic.variable.not.declared"
    const val PROPERTY_WARNING_SEMANTIC_VARIABLE_INIT = "warning.semantic.variable.init"
    const val PROPERTY_ERROR_SEMANTIC_COMMAND_NOT_ALLOWED = "error.semantic.command.not.allowed"
    const val PROPERTY_WARNING_SEMANTIC_COMMAND_UNKNOWN = "warning.semantic.command.unknown"
    const val PROPERTY_ERROR_SEMANTIC_COMMAND_ARGCOUNT = "error.semantic.command.argcount"
    const val PROPERTY_ERROR_SEMANTIC_INCOMPATIBLE_ARG = "error.semantic.incompatible.arg"
    const val PROPERTY_ERROR_SEMANTIC_COND_BOOLEAN = "error.semantic.cond.boolean"
    const val PROPERTY_ERROR_SEMANTIC_BINOPR_DIFFERENT = "error.semantic.binopr.different"
    const val PROPERTY_WARNING_SEMANTIC_VARIABLE_UNKNOWN = "warning.semantic.variable.unknown"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_REQUIRED_INITIALIZER = "error.semantic.variable.required.initializer"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_NOCONSTANT_INITIALIZER = "error.semantic.variable.noconstant.initializer"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_INITIALIZER = "error.semantic.variable.invalid.initializer"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_STRING_INITIALIZER = "error.semantic.variable.invalid.string.initializer"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_INITIALIZER_TYPE = "error.semantic.variable.invalid.initializer.type"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_INITIALIZER_STRINGADD = "error.semantic.variable.invalid.initializer.stringadd"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_NOTARRAY = "error.semantic.variable.notarray"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_DECLARE_CONST = "error.semantic.variable.declare.const"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_NOT_ARRAYSIZE = "error.semantic.variable.not.arraysize"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_ARRAYSIZE = "error.semantic.variable.invalid.arraysize"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_MAXARRAYSIZE = "error.semantic.variable.invalid.maxarraysize"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_ARRAYINITILIZER = "error.semantic.variable.invalid.arrayinitilizer"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_ARRAYSUBSCRIPT = "error.semantic.variable.invalid.arraysubscript"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_ARRAYOUTOFBOUNDS = "error.semantic.variable.invalid.arrayoutofbounds"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_UITYPE = "error.semantic.variable.invalid.uitype"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_UIINITIALIZER_COUNT = "error.semantic.variable.invalid.uiinitializer.count"
    const val PROPERTY_ERROR_SEMANTIC_VARIABLE_INVALID_UIINITIALIZER_TYPE = "error.semantic.variable.invalid.uiinitializer.type"
    const val PROPERTY_ERROR_SEMANTIC_ASSIGN_NOTVARIABLE = "error.semantic.assign.notvariable"
    const val PROPERTY_ERROR_SEMANTIC_ASSIGN_CONSTVARIABLE = "error.semantic.assign.constvariable"
    const val PROPERTY_ERROR_SEMANTIC_ASSIGN_TYPE_NOTCOMPATIBLE = "error.semantic.assign.type.notcompatible"
    const val PROPERTY_ERROR_SEMANTIC_SINGLE_OPERATOR_NUMONLY = "error.semantic.single.operator.numonly"
    const val PROPERTY_ERROR_SEMANTIC_SINGLE_OPERATOR_LNOT = "error.semantic.single.operator.lnot"
    const val PROPERTY_ERROR_SEMANTIC_STRING_OPERATOR_CONDITIONAL = "error.semantic.string.operator.conditional"
    const val PROPERTY_ERROR_SEMANTIC_ARRAY_ELEMENT_INTONLY = "error.semantic.array.element.intonly"
    const val PROPERTY_WARNING_SEMANTIC_CASEVALUE = "warning.semantic.casevalue"
    const val PROPERTY_ERROR_SEMANTIC_CASEVALUE_CONSTONLY = "error.semantic.casevalue.constonly"
    const val PROPERTY_ERROR_SEMANTIC_CONDITION_INVALID = "error.semantic.condition.invaliconst d"

    //--------------------------------------------------------------------------
    // サジェスト
    //--------------------------------------------------------------------------
    const val PROPERTY_WARNING_SEMANTIC_UNUSE_VARIABLE = "warning.semantic.unuse.variable"
    const val PROPERTY_WARNING_SEMANTIC_UNUSE_FUNCTION = "warning.semantic.unuse.function"
    const val PROPERTY_WARNING_TOOMUCH_LINECOUNT = "warning.toomuch.linecount"
    const val PROPERTY_WARNING_SEMANTIC_INFO_VARNAME = "warning.semantic.info.varname"

    /** 定義ファイルパス  */
    private val PROPERTIES_PATH : String

    /** 変数格納  */
    private val properties : Properties

    enum class Level
    {
        ERROR,
        WARNING, // KONTAKT上のコンパイルでエラーになるかもしれないレベルの警告
        IGNORE_WARNING, // シュリンクの提示や無視しても動作には支障がないと想定される警告
        INFO,
        DEBUG
    }

    /**
     * static initializer
     */
    init
    {
        val dir = ApplicationConstants.DATA_DIR + "/lang"
        PROPERTIES_PATH = "$dir/message"
        properties = Properties()
        try
        {
            val localizedSuffix = Locale.getDefault().language

            //
            // 全言語共通のプロパティをロード
            //
            load(properties, "$PROPERTIES_PATH.properties")

            //
            // 同じフォルダに +."国コード名(小文字)" があればマージ
            //
            if(localizedSuffix.isNotEmpty())
            {
                val path = PROPERTIES_PATH + "_" + localizedSuffix.toLowerCase() + ".properties"
                val file = File(path)
                if(file.exists())
                {
                    val p = Properties()
                    load(p, path)
                    for(key in p.stringPropertyNames())
                    {
                        properties.setProperty(key, p.getProperty(key))
                    }
                }
            }

        }
        catch(e : Throwable)
        {
            e.printStackTrace()
            exitProcess(1)
        }

    }

    /**
     * Loading
     */
    @Throws(IOException::class)
    private fun load(dest : Properties, path : String)
    {
        var reader : InputStreamReader? = null
        try
        {
            reader = InputStreamReader(FileInputStream(path), "utf-8")
            dest.load(reader)
        }
        finally
        {
            StreamCloser.close(reader!!)
        }
    }

    /**
     * 文法解析中のエラーメッセージを設定ファイル「message.properties」の書式に従い展開
     */
    fun expand(src : ParseException) : String
    {
        return expand(
            PROPERTY_ERROR_SYNTAX,
            Level.ERROR,
            src.currentToken.beginLine,
            src.currentToken.beginColumn,
            src.currentToken.image.length
        )
    }

    fun expand(propertyKey : String, level : Level, errorLine : Int, errorColumn : Int, tokenLen : Int) : String
    {
        var len = tokenLen
        //
        // ${level}       :エラーレベル
        // ${line}        :行
        // ${colmn}       :位置（トークンの開始）
        // ${tokenLen}    :トークンの文字列長
        //
        if(len <= 0)
        {
            len = Int.MAX_VALUE
        }
        var message = properties.getProperty(propertyKey)
        message = message.replace("\${level}", "" + level.toString())
        message = message.replace("\${line}", "" + errorLine)
        message = message.replace("\${colmn}", "" + errorColumn)
        message = message.replace("\${tokenLen}", "" + len)
        return message
    }

    /**
     * 標準出力に出力する
     */
    fun println(src : ParseException)
    {
        println(expand(src))
    }

    /**
     * 標準出力に出力する
     */
    fun println(propertyKey : String, level : Level, errorLine : Int, errorColumn : Int, tokenLen : Int)
    {
        val message = expand(propertyKey, level, errorLine, errorColumn, tokenLen)
        println(message)
    }

    /**
     * 標準出力に出力する
     */
    fun println(propertyKey : String, level : Level, symbol : SymbolDefinition, vararg ext : String)
    {
        var extIndex = 1
        var message = expand(propertyKey, level, symbol.position.beginLine, symbol.position.beginColumn, symbol.name.length)
        message = message.replace("\${symbolname}", symbol.name)
        for(s in ext)
        {
            message = message.replace("\${ext$extIndex}", s)
            extIndex++
        }
        println(message)
    }

    /**
     * 標準出力に出力する(レベル：ERROR)
     */
    fun printlnE(propertyKey : String, symbol : SymbolDefinition, vararg ext : String)
    {
        println(propertyKey, Level.ERROR, symbol, *ext)
    }

    /**
     * 標準出力に出力する(レベル：WARN)
     */
    fun printlnW(propertyKey : String, symbol : SymbolDefinition, vararg ext : String)
    {
        println(propertyKey, Level.WARNING, symbol, *ext)
    }

    /**
     * 標準出力に出力する(レベル：INFO)
     */
    fun printlnI(propertyKey : String, symbol : SymbolDefinition, vararg ext : String)
    {
        println(propertyKey, Level.INFO, symbol, *ext)
    }

    /**
     * 標準出力に出力する(レベル：DEBUG)
     */
    fun printlnD(propertyKey : String, symbol : SymbolDefinition, vararg ext : String)
    {
        println(propertyKey, Level.DEBUG, symbol, *ext)
    }
}
