package com.twiceyuan.retrokv.annotations

import com.twiceyuan.retrokv.KeyValue
import com.twiceyuan.retrokv.adapters.StorageAdapter
import java.lang.reflect.Type

/**
 * Created by twiceYuan on 23/01/2017.
 *
 *
 * KeyValue 项构造器
 */
class PreferenceBuilder(val key: String, val type: Type, val adapter: StorageAdapter) {

    fun build(): KeyValue<Any> {
        return object : KeyValue<Any> {
            override fun set(t: Any?) {
                adapter.saveValue(key, type, t)
            }

            override fun get(): Any? {
                return adapter.readValue(key, type)
            }

            override fun remove() {
                adapter.saveValue(key, type, null)
            }
        }
    }
}
