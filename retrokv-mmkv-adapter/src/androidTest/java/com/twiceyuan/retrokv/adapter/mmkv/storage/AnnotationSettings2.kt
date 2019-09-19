package com.twiceyuan.retrokv.adapter.mmkv.storage

import com.twiceyuan.retrokv.KVStorage
import com.twiceyuan.retrokv.KeyValue
import com.twiceyuan.retrokv.annotations.KeyName

/**
 * Created by twiceYuan on 20/01/2017.
 *
 * Mock setting items
 */
interface AnnotationSettings2 : KVStorage {

    @KeyName("is_login")
    fun isLogin(): KeyValue<Boolean>

    @KeyName("launch_count")
    fun launchCount(): KeyValue<Int>

    @KeyName("user_points")
    fun userPoints(): KeyValue<Float>

    @KeyName("last_login")
    fun lastLogin(): KeyValue<Long>

    @KeyName("username")
    fun username(): KeyValue<String>

    @KeyName("user_tags")
    fun userTags(): KeyValue<Set<String>>
}
