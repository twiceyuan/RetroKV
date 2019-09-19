package com.twiceyuan.retrokv

/**
 * Created by twiceYuan on 20/01/2017.

 * Preference Adapter
 */
interface Preference<T> {
    fun set(t: T?)
    fun get(): T?
    fun remove()
}
