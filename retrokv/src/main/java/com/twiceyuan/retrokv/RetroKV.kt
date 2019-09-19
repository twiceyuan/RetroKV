package com.twiceyuan.retrokv

import com.twiceyuan.retrokv.adapters.StorageAdapterFactory
import com.twiceyuan.retrokv.annotations.KeyName
import com.twiceyuan.retrokv.annotations.KeyParam
import com.twiceyuan.retrokv.annotations.PreferenceBuilder
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Proxy

/**
 * Created by twiceYuan on 20/01/2017.
 *
 * Build preferences helper class that user defined.
 */
class RetroKV {

    class Builder {

        private var adapterFactory: StorageAdapterFactory<*>? = null

        fun setAdapterFactory(factory: StorageAdapterFactory<*>) = apply {
            adapterFactory = factory
        }

        fun build() = RetroKV().apply {
            this@apply.adapterFactory = this@Builder.adapterFactory
        }
    }

    var adapterFactory: StorageAdapterFactory<*>? = null

    inline fun <reified T : KVStorage> create(instanceName: String): T =
            createInstance(instanceName, T::class.java)

    inline fun <reified T : KVStorage> create(): T =
            createInstance(T::class.java.simpleName, T::class.java)

    fun <T : KVStorage> createInstance(storageClass: Class<T>): T {
        return createInstance(storageClass.simpleName, storageClass)
    }

    fun <T : KVStorage> createInstance(instanceName: String, storageClass: Class<T>): T {
        val factory: StorageAdapterFactory<*> = adapterFactory ?: throw IllegalArgumentException(
                "Please set adapter factory first"
        )

        val loader = storageClass.classLoader
        val implementClassed = arrayOf<Class<*>>(storageClass)
        val adapter = factory.create(instanceName)

        @Suppress("UNCHECKED_CAST")
        return Proxy.newProxyInstance(loader, implementClassed, InvocationHandler { _, method, params ->
            if (method.name == KVStorage::clear.name) {
                adapter.clear()
                return@InvocationHandler null
            }

            if (method.name == KVStorage::allKeys.name) {
                return@InvocationHandler adapter.allKeys()
            }

            val key = getKeyNameFromMethod(method, params)
            val returnType = method.genericReturnType
            val preferenceType = getParameterUpperBound(0, returnType as ParameterizedType)
            PreferenceBuilder(key, preferenceType, adapter).build()
        }) as T
    }

    /**
     * 获得方法的 key 值。优先使用注解值，注解值不存在时使用方法名作为 key

     * @param method 定义的方法集接口方法
     * @param params 方法的参数，需要取得定义了 KeyParam 的参数作为 key 的一部分
     * @return 该项 KeyValue 的 key 值
     */
    private fun getKeyNameFromMethod(method: Method, params: Array<Any>): String {

        val annotations = method.annotations

        val keyNameAnnotation: KeyName? = annotations.firstOrNull { it is KeyName }?.let { it as KeyName }

        // 优先使用 KeyName 注解的值，没有时使用 Method 的名称
        var key: String = keyNameAnnotation?.value ?: method.name

        // 如果函数没有参数，则 key 构造完毕
        if (params.isEmpty()) {
            return key
        }

        // 如果函数有被 KeyParam 注解的参数，则在 key 后追加 N 个 "#paramKey=paramValue" 的形式作为 Key
        val parameterAnnotations = method.parameterAnnotations
        for (index in (params.indices)) {
            val keyParamAnnotation: KeyParam? = parameterAnnotations[index]
                    .firstOrNull { it is KeyParam }
                    ?.let { it as KeyParam }
            if (keyParamAnnotation != null) {
                key = "$key#${keyParamAnnotation.value}=${params[index]}"
            }
        }

        return key
    }
}

internal const val TAG = "RetroRV"