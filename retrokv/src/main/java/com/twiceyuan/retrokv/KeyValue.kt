package com.twiceyuan.retrokv

/**
 * Created by twiceYuan on 20/01/2017.

 * KeyValue Interface
 */
interface KeyValue<T> {
    fun set(t: T?)
    fun get(): T?
    fun remove()
}
