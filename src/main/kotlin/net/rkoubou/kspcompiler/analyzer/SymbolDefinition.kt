/* =========================================================================

    SymbolDefinition.java
    Copyright (c) R-Koubou

   ======================================================================== */

package net.rkoubou.kspcompiler.analyzer

/**
 * ASTノード、ノード中に宣言されたシンボルの中間状態を表現する
 */
open class SymbolDefinition : AnalyzerConstants
{

    /** シンボルの種類  */
    var symbolType = SymbolType.Unknown
    /** シンボルテーブルインデックス値  */
    var index = -1
    /** データ型  */
    var type = AnalyzerConstants.TYPE_NONE
    /** アクセス識別フラグ（ある場合に使用。未使用の場合は0）  */
    var accessFlag = 0
    /** 実行環境で予約済みのシンボルかどうか）  */
    var reserved = false
    /** 識別子名  */
    open var name = ""
    get()
    {
        return if(this.obfuscatedName.isNotEmpty())
        {
            this.obfuscatedName
        }
        else field
    }

    /** オブファスケート後の識別子名  */
    var obfuscatedName : String = ""
    /** 状態  */
    var state : AnalyzerConstants.SymbolState = AnalyzerConstants.SymbolState.UNLOADED
    /** 意味解析フェーズ中に走査し参照されたかを記録する  */
    var referenced = false
    /** 意味解析フェーズ中に走査し参照された回数を記録する  */
    var referenceCount = 0
    /** accessFlagにACCESS_ATTR_UIが含まれている場合のUIタイプの識別子名  */
    var uiTypeName = ""
    /** 値がある場合はその値(Integer,Double,String,int[],double[],String[])  */
    var value : Any? = null
    /** 定義した行・列情報  */
    val position = Position()

    /**
     * 定数かどうかを判定する
     */
    val isConstant : Boolean
        get() = isConstant(accessFlag)

    /**
     * このシンボルの型がIntegerかどうかを判別する
     */
    val isInt : Boolean
        get() = isInt(primitiveType)

    /**
     * このシンボルの型がRealかどうかを判別する
     */
    val isReal : Boolean
        get() = isReal(primitiveType)

    /**
     * このシンボルの型がStringかどうかを判別する
     */
    val isString : Boolean
        get() = isString(primitiveType)

    /**
     * このシンボルの型がBooleanかどうかを判別する
     */
    val isBoolean : Boolean
        get() = isBoolean(primitiveType)

    /**
     * このシンボルの型が配列属性が付いているかどうかを判別する
     */
    val isArray : Boolean
        get() = isArray(type)

    /**
     * UI変数かどうかを判定する
     */
    val isUIVariable : Boolean
        get() = isUIVariable(accessFlag)

    /**
     * ポリフォニック変数かどうかを判定する
     */
    val isPolyphonicVariable : Boolean
        get() = isPolyphonicVariable(accessFlag)

    /**
     * #TYPE_VOID フラグがONかどうかを判定する
     */
    val isVoid : Boolean
        get() = isVoid(type)

    /**
     * 数値型かどうかを判定する
     */
    val isNumeral : Boolean
        get() = isNumeral(type)

    /**
     * プリプロセッサシンボルなど、データ型(数値・文字列)に
     * 当てはまらないデータ型かどうかを判定する
     */
    val isNonVariableType : Boolean
        get() = isNonVariableType(type)

    /**
     * 配列情報フラグ等を含まない、純粋なプリミティブ型の識別値を取得する。
     * 型の識別値のビットフラグを返す個別の判定は isInt()、isReal()、isString() 等を使用すること。
     */
    val primitiveType : Int
        get() = type and AnalyzerConstants.TYPE_MASK

    /**
     * 型の付随識別情報のビットフラグを返す。個別の判定は isArray() 等を使用すること。
     */
    val typeAttribute : Int
        get() = type and AnalyzerConstants.TYPE_ATTR_MASK

    /**
     * シンボルの型データから初期値を生成し戻り値として返す
     * @return Integer, Double, String, Boolean インスタンスのいずれか。typeが該当しない場合は DEFAULT_VALUE_DUMMY
     */
    val defaultValue : Any
        get() = getDefaultValue(type)

    /**
     * シンボル名を元に文字列表現された型情報を返す
     */
    val typeName : String
        get() = getTypeName(name)

    enum class SymbolType
    {
        Unknown,
        Callback,
        Command,
        UserFunction,
        Variable,
        Literal,
        Expression,
        PreprocessorSymbol
    }

    /**
     * Ctor.
     */
    constructor()
    {
    }

    /**
     * コピーコンストラクタ
     */
    constructor(src : SymbolDefinition)
    {
        SymbolDefinition.copy(src, this)
    }

    /**
     * このシンボルのダンプ情報を取得する
     */
    fun dump() : String
    {
        val sb = stringBuffer
        sb.delete(0, sb.length)
        sb.append("---- Symbol Information -----------------------------").append('\n')
        sb.append("symbolType:").append(symbolType).append('\n')
        sb.append("index:").append(index).append('\n')
        sb.append("type:").append(getTypeName(type)).append('\n')
        sb.append("accessFlag:").append("0x").append(Integer.toHexString(accessFlag)).append('\n')
        sb.append("reserved:").append(reserved).append('\n')
        sb.append("name:").append(name).append('\n')
        sb.append("obfuscatedName:").append(this.obfuscatedName).append('\n')
        sb.append("uiTypeName:").append(uiTypeName).append('\n')
        sb.append("value:").append(value).append('\n')

        return sb.toString()
    }

    /**
     * このシンボル名を取得する。
     * @param originalName オブファスケート前のシンボル名を取得するかどうか
     */
    fun getName(originalName : Boolean) : String?
    {
        return if(originalName)
        {
            name
        }
        else this.obfuscatedName
    }

    /**
     * シンボル名の1文字目の記号から変数の型情報を算出する
     */
    fun setTypeFromVariableName() : Boolean
    {
        this.type = getKSPTypeFromVariableName(this.name)
        return this.type != AnalyzerConstants.TYPE_NONE
    }

    /**
     * valueに指定された値のIntegerインスタンスを割り当てる
     */
    fun setValue(v : Int)
    {
        check(isInt) { "$name :  Type is $typeName" }
        this.value = v
    }

    /**
     * valueに指定された値のint[]インスタンスを割り当てる
     */
    fun setValue(v : IntArray)
    {
        check(!(!isInt || !isArray)) { "Type is $typeName" }
        val newV = v.copyOf(v.size)
        this.value = newV
    }

    /**
     * valueに指定された値のDoubleインスタンスを割り当てる
     */
    fun setValue(v : Double)
    {
        check(isReal) { "Type is $typeName" }
        this.value = v
    }

    /**
     * valueに指定された値のint[]インスタンスを割り当てる
     */
    fun setValue(v : DoubleArray)
    {
        check(!(!isReal || !isArray)) { "Type is $typeName" }
        val newV = v.copyOf(v.size)
        this.value = newV
    }

    /**
     * valueに指定された値のStringインスタンスを割り当てる
     */
    fun setValue(v : String)
    {
        check(isString) { "Type is $typeName" }
        this.value = v
    }

    /**
     * valueに指定された値のint[]インスタンスを割り当てる
     */
    fun setValue(v : Array<String>)
    {
        check(!(!isString || !isArray)) { "Type is $typeName" }
        val newV = v.copyOf(v.size)
        this.value = newV
    }

    /**
     * valueに指定された値のBooleanインスタンスを割り当てる
     */
    fun setValue(v : Boolean?)
    {
        check(isBoolean) { "Type is $typeName" }
        this.value = v
    }

    /**
     * valueに指定された値のboolean[]インスタンスを割り当てる
     */
    fun setValue(v : BooleanArray)
    {
        check(!(!isBoolean || !isArray)) { "Type is $typeName" }
        val newV = v.copyOf(v.size)
        this.value = newV
    }

    /**
     * このシンボルが持つ型識別値が複数のデータ型ビットがONになっているかどうかを判定する
     * 戻り値など、暗黙の型変換の可能性がある式やコマンドで使用する。
     */
    fun hasMultipleType() : Boolean
    {
        return hasMultipleType(type)
    }

    /**
     * プリプロセッサシンボルなど、データ型(数値・文字列)に
     * 当てはまらないデータ型の1文字目の検証を行う（[0-9]以外で始まるかどうか）
     */
    fun validateNonVariablePrefix() : Boolean
    {
        return validateNonVariablePrefix(name)
    }

    /**
     * 型識別情報のビットフラグを明示的に指定する
     * @param typeFlag TYPE_#### TYPE_ATTR_####
     * @param accessFlag ACCESS_ATTR_####
     */
    fun setTypeFlag(typeFlag : Int, accessFlag : Int)
    {
        this.type = typeFlag
        this.accessFlag = accessFlag
    }

    /**
     * 型の付随識別情報のビットフラグに指定されたフラグを元に該当ビットをONにする
     * @param typeFlag TYPE_#### TYPE_ATTR_####
     * @param accessFlag ACCESS_ATTR_####
     */
    fun addTypeFlag(typeFlag : Int, accessFlag : Int)
    {
        this.type = this.type or typeFlag
        this.accessFlag = this.accessFlag or accessFlag
    }

    /**
     * 型の付随識別情報のビットフラグに指定されたフラグを元に該当ビットをOFFにする
     * @param typeFlag TYPE_ATTR_####
     * @param accessFlag ACCESS_ATTR_####
     */
    fun removeTypeFlag(typeFlag : Int, accessFlag : Int)
    {
        this.type = this.type and typeFlag.inv()
        this.accessFlag = this.accessFlag and accessFlag.inv()
    }

    /**
     * シンボルの型データからKSPの定める型式別の記号に変換する
     */
    fun toKSPTypeCharacter() : String
    {
        return toKSPTypeCharacter(type)
    }

    /**
     * シンボル名表現に変換する
     */
    override fun toString() : String
    {
        return name
    }

    companion object
    {

        /** ワークバッファ  */
        private val stringBuffer = StringBuilder(512)

        /**
         * 値コピー
         */
        @JvmOverloads
        fun copy(src : SymbolDefinition, dest : SymbolDefinition, copyPosition : Boolean = true)
        {
            if(src === dest)
            {
                // 参照先が同じなので何もしない
                return
            }
            dest.symbolType = src.symbolType
            dest.index = src.index
            dest.type = src.type
            dest.accessFlag = src.accessFlag
            dest.reserved = src.reserved
            dest.name = src.name
            dest.uiTypeName = src.uiTypeName
            dest.value = src.value
            if(copyPosition)
            {
                dest.position.copy(src.position)
            }
        }

        /**
         * valueに指定された値を割り当てる(フィールド変数valueの浅いコピー)
         */
        fun setValue(src : SymbolDefinition, dest : SymbolDefinition)
        {
            dest.value = src.value
        }

        /**
         * 与えられた属性フラグ情報から定数かどうかを判定する
         */
        fun isConstant(sym1 : SymbolDefinition, sym2 : SymbolDefinition) : Boolean
        {
            return sym1.isConstant && sym2.isConstant
        }

        /**
         * 与えられた属性フラグ情報から定数かどうかを判定する
         */
        fun isConstant(accessFlag : Int) : Boolean
        {
            return accessFlag and AnalyzerConstants.ACCESS_ATTR_CONST != 0
        }

        /**
         * 与えられた型識別値がIntegerかどうかを判別する
         */
        fun isInt(sym1 : SymbolDefinition, sym2 : SymbolDefinition) : Boolean
        {
            return sym1.isInt && sym2.isInt
        }

        /**
         * 与えられた型識別値がIntegerかどうかを判別する
         */
        fun isInt(type : Int) : Boolean
        {
            return type and AnalyzerConstants.TYPE_INT != 0
        }

        /**
         * 与えられた型識別値がRealかどうかを判別する
         */
        fun isReal(sym1 : SymbolDefinition, sym2 : SymbolDefinition) : Boolean
        {
            return sym1.isReal && sym2.isReal
        }

        /**
         * 与えられた型識別値がRealかどうかを判別する
         */
        fun isReal(type : Int) : Boolean
        {
            return type and AnalyzerConstants.TYPE_REAL != 0
        }

        /**
         * 与えられた型識別値がStringかどうかを判別する
         */
        fun isString(sym1 : SymbolDefinition, sym2 : SymbolDefinition) : Boolean
        {
            return sym1.isString && sym2.isString
        }

        /**
         * 与えられた型識別値がStringかどうかを判別する
         */
        fun isString(type : Int) : Boolean
        {
            return type and AnalyzerConstants.TYPE_STRING != 0
        }

        /**
         * 与えられた型識別値がbooleanかどうかを判別する
         */
        fun isBoolean(sym1 : SymbolDefinition, sym2 : SymbolDefinition) : Boolean
        {
            return sym1.isBoolean && sym2.isBoolean
        }

        /**
         * 与えられた型識別値がbooleanかどうかを判別する
         */
        fun isBoolean(type : Int) : Boolean
        {
            return type and AnalyzerConstants.TYPE_BOOL != 0
        }

        /**
         * 与えられた型識別値が配列属性を含むかどうかを判別する
         */
        fun isArray(sym1 : SymbolDefinition, sym2 : SymbolDefinition) : Boolean
        {
            return sym1.isArray && sym2.isArray
        }

        /**
         * 与えられた型識別値が配列属性を含むかどうかを判別する
         */
        fun isArray(type : Int) : Boolean
        {
            return type and AnalyzerConstants.TYPE_ATTR_ARRAY != 0
        }

        /**
         * 与えられた属性フラグ情報からUI変数かどうかを判定する
         */
        fun isUIVariable(accessFlag : Int) : Boolean
        {
            return accessFlag and AnalyzerConstants.ACCESS_ATTR_UI != 0
        }

        /**
         * 与えられた属性フラグ情報からポリフォニック変数かどうかを判定する
         */
        fun isPolyphonicVariable(accessFlag : Int) : Boolean
        {
            return accessFlag and AnalyzerConstants.ACCESS_ATTR_POLY != 0
        }

        /**
         * 与えられた型識別値がVoidかどうかを判別する
         */
        fun isVoid(sym1 : SymbolDefinition, sym2 : SymbolDefinition) : Boolean
        {
            return sym1.isVoid && sym2.isVoid
        }

        /**
         * 与えられた型識別値がVoidかどうかを判別する
         */
        fun isVoid(type : Int) : Boolean
        {
            return type and AnalyzerConstants.TYPE_VOID != 0
        }

        /**
         * 与えられた型識別値から数値型かどうかを判定する
         */
        fun isNumeral(sym1 : SymbolDefinition, sym2 : SymbolDefinition) : Boolean
        {
            return sym1.isNumeral && sym2.isNumeral
        }

        /**
         * 与えられた型識別値から数値型かどうかを判定する
         */
        fun isNumeral(t : Int) : Boolean
        {
            return when(t and AnalyzerConstants.TYPE_MASK)
            {
                AnalyzerConstants.TYPE_INT, AnalyzerConstants.TYPE_REAL -> true
                else                                                    -> false
            }
        }

        /**
         * 与えられた変数名から数値型かどうかを判定する
         */
        fun isNumeralFromVariableName(variableName : String) : Boolean
        {
            return isNumeral(getKSPTypeFromVariableName(variableName))
        }

        /**
         * 与えられた型識別値から複数のデータ型ビットがONになっているかどうかを判定する
         * 戻り値など、暗黙の型変換の可能性がある式やコマンドで使用する。
         */
        fun hasMultipleType(type : Int) : Boolean
        {
            var cnt = 0
            for(i in 0 until AnalyzerConstants.TYPE_BIT_SIZE)
            {
                if(type and (1 shl i) != 0)
                {
                    cnt++
                }
            }
            return cnt >= 2
        }

        /**
         * プリプロセッサシンボルなど、データ型(数値・文字列)に
         * 当てはまらないデータ型かどうかを判定する
         */
        fun isNonVariableType(type : Int) : Boolean
        {
            return type and AnalyzerConstants.TYPE_NON_VARIABLE != 0
        }

        /**
         * プリプロセッサシンボルなど、データ型(数値・文字列)に
         * 当てはまらないデータ型の1文字目の検証を行う（[0-9]以外で始まるかどうか）
         */
        fun validateNonVariablePrefix(name : String) : Boolean
        {
            return Regex(AnalyzerConstants.REGEX_NON_TYPE_PREFIX).find(name) != null
        }

        /**
         * 配列情報フラグ等を含まない、純粋なプリミティブ型の識別値を取得する。
         * 型の識別値のビットフラグを返す個別の判定は isInt()、isReal()、isString() 等を使用すること。
         */
        fun getPrimitiveType(type : Int) : Int
        {
            return type and AnalyzerConstants.TYPE_MASK
        }

        /**
         * 型識別情報のビットフラグを明示的に指定する
         */
        fun setTypeFlag(src : SymbolDefinition, dest : SymbolDefinition)
        {
            dest.setTypeFlag(src.type, src.accessFlag)
        }

        /** value の初期値のダミー   */
        val DEFAULT_VALUE_DUMMY = Any()

        /**
         * シンボルの型データから初期値を生成し戻り値として返す
         * @return Integer, Double, String インスタンスのいずれか。
         */
        fun getDefaultValue(type : Int) : Any
        {
            return when(getPrimitiveType(type))
            {
                AnalyzerConstants.TYPE_INT    -> 0
                AnalyzerConstants.TYPE_REAL   -> 0
                AnalyzerConstants.TYPE_STRING -> ""
                //--------------------------------------------------------------------------
                // 内部処理用
                //--------------------------------------------------------------------------
                AnalyzerConstants.TYPE_BOOL   -> true
                else                          -> DEFAULT_VALUE_DUMMY
            }
        }

        /**
         * KSPの変数名を元に文字列表現された型情報を返す
         */
        fun getTypeName(type : Int) : String
        {
            return getTypeName(toKSPTypeCharacter(type))
        }

        /**
         * KSPの変数名を元に文字列表現された型情報を返す
         */
        fun getTypeName(name : String) : String
        {
            if(name.isEmpty())
            {
                return "Unknown"
            }

            when(name[0])
            {
                '$'  -> return "Integer"
                '%'  -> return "Integer Array"
                '~'  -> return "Real"
                '?'  -> return "Real Array"
                '@'  -> return "String"
                '!'  -> return "String Array"
                //--------------------------------------------------------------------------
                // 内部処理用
                //--------------------------------------------------------------------------
                'B'  -> return "Boolean"
                'V'  -> return "Void"
                'P'  -> return "Preprocessor"
                'K'  -> return "KeyID"
                '*'  -> return "any"
                else ->
                {
                    return if(Regex(AnalyzerConstants.REGEX_NON_TYPE_PREFIX).find(name) != null)
                    {
                        "Preprocessor Symbol or Key ID"
                    }
                    else "Unknown"
                }
            }
        }

        /**
         * シンボル名の1文字目の記号から型情報を算出する
         */
        fun getKSPTypeFromVariableName(variableName : String) : Int
        {
            if(variableName.isEmpty())
            {
                return AnalyzerConstants.TYPE_NONE
            }
            val t = variableName[0]

            if(Regex(AnalyzerConstants.REGEX_NON_TYPE_PREFIX).find(variableName) != null)
            {
                return AnalyzerConstants.TYPE_PREPROCESSOR_SYMBOL or AnalyzerConstants.TYPE_KEYID
            }

            when(t)
            {
                '$'  -> return AnalyzerConstants.TYPE_INT
                '%'  -> return AnalyzerConstants.TYPE_INT or AnalyzerConstants.TYPE_ATTR_ARRAY
                '~'  -> return AnalyzerConstants.TYPE_REAL
                '?'  -> return AnalyzerConstants.TYPE_REAL or AnalyzerConstants.TYPE_ATTR_ARRAY
                '@'  -> return AnalyzerConstants.TYPE_STRING
                '!'  -> return AnalyzerConstants.TYPE_STRING or AnalyzerConstants.TYPE_ATTR_ARRAY
                //--------------------------------------------------------------------------
                // 内部処理用
                //--------------------------------------------------------------------------
                'B'  -> return AnalyzerConstants.TYPE_BOOL
                'V'  -> return AnalyzerConstants.TYPE_VOID
                'P'  -> return AnalyzerConstants.TYPE_PREPROCESSOR_SYMBOL
                'K'  -> return AnalyzerConstants.TYPE_KEYID
                '*'  -> return AnalyzerConstants.TYPE_MULTIPLE

                else -> throw IllegalArgumentException("unknown ksp type : $t:$variableName")
            }
        }

        /**
         * シンボルの型データからKSPの定める型式別の記号に変換する
         */
        fun toKSPTypeCharacter(type : Int) : String
        {
            when(type)
            {
                AnalyzerConstants.TYPE_INT                                         -> return "$"
                AnalyzerConstants.TYPE_INT or AnalyzerConstants.TYPE_ATTR_ARRAY    -> return "%"
                AnalyzerConstants.TYPE_REAL                                        -> return "~"
                AnalyzerConstants.TYPE_REAL or AnalyzerConstants.TYPE_ATTR_ARRAY   -> return "?"
                AnalyzerConstants.TYPE_STRING                                      -> return "@"
                AnalyzerConstants.TYPE_STRING or AnalyzerConstants.TYPE_ATTR_ARRAY -> return "!"
                //--------------------------------------------------------------------------
                // 内部処理用
                //--------------------------------------------------------------------------
                AnalyzerConstants.TYPE_BOOL                                        -> return "B"
                AnalyzerConstants.TYPE_VOID                                        -> return "V"
                AnalyzerConstants.TYPE_PREPROCESSOR_SYMBOL                         -> return "P"
                AnalyzerConstants.TYPE_KEYID                                       -> return "K"
                AnalyzerConstants.TYPE_MULTIPLE                                    -> return "*"
                else                                                               -> return "{UNKNOWN:$type}"
            }
        }

        /**
         * シンボルの型データからKSPの定める型式別の記号に変換する
         */
        fun toKSPTypeName(type : Int) : String
        {
            return getTypeName(toKSPTypeCharacter(type))
        }
    }
}
