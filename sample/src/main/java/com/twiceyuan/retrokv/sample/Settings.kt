package com.twiceyuan.retrokv.sample

import com.twiceyuan.retrokv.KVStorage
import com.twiceyuan.retrokv.KeyValue

/**
 * Created by twiceYuan on 20/01/2017.

 * Mock Settings
 */
interface Settings : KVStorage {

    fun sampleInt(): KeyValue<Int>

    fun sampleString(): KeyValue<String>

    /**
     * Save current user instance
     */
    fun sampleParcelable(): KeyValue<User>
}
