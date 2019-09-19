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
     * *
     * @return 该项 sp 的 key 值
     */
    private fun getKeyNameFromMethod(method: Method): String {
        val annotations = method.annotations
        if (annotations.isEmpty()) {
            return method.name
        }

        if (annotations.size != 1) {
            throw KeyNameError(method)
        }

        val annotation = annotations[0]
        if (annotation is KeyName) {
            return annotation.value
        } else {
            throw KeyNameError(method, annotation)
        }
    }
}

internal const val TAG = "RetroRV"