package com.twiceyuan.retrokv.adapter.mmkv

import android.content.Context
import android.os.Parcelable
import android.util.Log
import com.tencent.mmkv.MMKV
import com.twiceyuan.retrokv.adapters.StorageAdapter
import com.twiceyuan.retrokv.adapters.StorageAdapterFactory
import com.twiceyuan.retrokv.getParameterUpperBound
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

private const val TAG = "MmkvAdapter"

interface MmkvInstanceCreator {
    fun create(instanceName: String): MMKV
}

interface MmkvInitializer {
    fun init()
}

/**
 * MMKV 默认实例构造器
 */
private val defaultCreator = object : MmkvInstanceCreator {

    override fun create(instanceName: String): MMKV {
        return MMKV.mmkvWithID(instanceName)
    }
}

/**
 * 适配器工厂类
 */
class MmkvAdapterFactory(
        initializer: MmkvInitializer,
        private val creator: MmkvInstanceCreator = defaultCreator
) : StorageAdapterFactory<MmkvAdapter> {

    /**
     * 默认初始化器，传入 Context 进行初始化，需要定制初始化过程请传入 initializer
     */
    constructor(context: Context) : this(initializer = object : MmkvInitializer {
        override fun init() {
            MMKV.initialize(context)
        }
    })

    init {
        initializer.init()
    }

    override fun create(instanceName: String): MmkvAdapter {
        // 需要定制实例创建过程请构造时传入 creator
        return MmkvAdapter(creator.create(instanceName))
    }
}

/**
 * 使用 MMKV 作为 RetroKV 的存储容器的适配器
 */
class MmkvAdapter(private val mmkv: MMKV) : StorageAdapter {

    enum class SupportedType {
        BOOLEAN,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        STRING,
        STRING_SET,
        BYTE_ARRAY,
        PARCELABLE,
        NOT_SUPPORT,
    }

    /**
     * 判断类型是 SharedPreferences 支持的哪种类型
     */
    private fun Type.toSupportedType(): SupportedType = when (this) {

        Boolean::class.java, java.lang.Boolean::class.java ->
            SupportedType.BOOLEAN

        Int::class.java, Integer::class.java, Int::class, Integer::class ->
            SupportedType.INT

        Long::class.java, java.lang.Long::class.java ->
            SupportedType.LONG

        Float::class.java, java.lang.Float::class.java ->
            SupportedType.FLOAT

        Double::class.java, java.lang.Double::class.java ->
            SupportedType.DOUBLE

        String::class.java, java.lang.String::class.java ->
            SupportedType.STRING

        ByteArray::class.java ->
            SupportedType.BYTE_ARRAY

        is ParameterizedType -> {
            val rawType = rawType as Class<*>
            val genericType = getParameterUpperBound(0, this) as Class<*>

            if (Set::class.java.isAssignableFrom(rawType) && genericType == String::class.java) {
                SupportedType.STRING_SET
            } else {
                SupportedType.NOT_SUPPORT
            }
        }

        else -> {
            val clazz = this as Class<*>
            if (Parcelable::class.java.isAssignableFrom(clazz)) {
                SupportedType.PARCELABLE
            } else {
                SupportedType.NOT_SUPPORT
            }
        }
    }

    override fun saveValue(key: String, type: Type, value: Any?) {
        if (value == null) {
            mmkv.removeValueForKey(key)
            return
        }
        @Suppress("UNCHECKED_CAST")
        when (type.toSupportedType()) {
            SupportedType.BOOLEAN -> mmkv.encode(key, value as Boolean)
            SupportedType.INT -> mmkv.encode(key, value as Int)
            SupportedType.LONG -> mmkv.encode(key, value as Long)
            SupportedType.FLOAT -> mmkv.encode(key, value as Float)
            SupportedType.DOUBLE -> mmkv.encode(key, value as Double)
            SupportedType.STRING -> mmkv.encode(key, value as String)
            SupportedType.STRING_SET -> mmkv.encode(key, value as Set<String>)
            SupportedType.BYTE_ARRAY -> mmkv.encode(key, value as ByteArray)
            SupportedType.PARCELABLE -> mmkv.encode(key, value as Parcelable)
            SupportedType.NOT_SUPPORT -> {
                Log.e(TAG, "[saveValue]${this::class.java.name} not support type: $type")
            }
        }
    }

    override fun readValue(key: String, type: Type): Any? {
        if (!mmkv.containsKey(key)) {
            return null
        }

        // Catch 类发生变化的情况，打印日志并返回 null
        return try {
            readValue(type, key)
        } catch (e: ClassCastException) {
            Log.i(TAG, "Value of [key=$key] was changed: ${e.message}")
            null
        }
    }

    private fun readValue(type: Type, key: String): Any? {
        @Suppress("UNCHECKED_CAST")
        return when (type.toSupportedType()) {
            SupportedType.BOOLEAN -> mmkv.decodeBool(key)
            SupportedType.INT -> mmkv.decodeInt(key)
            SupportedType.LONG -> mmkv.decodeLong(key)
            SupportedType.FLOAT -> mmkv.decodeFloat(key)
            SupportedType.DOUBLE -> mmkv.decodeDouble(key)
            SupportedType.STRING -> mmkv.decodeString(key)
            SupportedType.STRING_SET -> mmkv.decodeStringSet(key)
            SupportedType.BYTE_ARRAY -> mmkv.decodeBytes(key)
            SupportedType.PARCELABLE -> mmkv.decodeParcelable(key, type as Class<out Parcelable>)
            SupportedType.NOT_SUPPORT -> {
                Log.e(TAG, "[readValue]${this::class.java.name} not support type: $type")
                return null
            }
        }
    }

    override fun clear() {
        mmkv.clearAll()
    }

    override fun allKeys(): Set<String> {
        return mmkv.allKeys()?.toSet() ?: HashSet()
    }
}