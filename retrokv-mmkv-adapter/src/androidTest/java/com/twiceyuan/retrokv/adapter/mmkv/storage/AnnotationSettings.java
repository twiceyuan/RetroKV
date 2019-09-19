package com.twiceyuan.retrokv.adapter.mmkv.storage;

import com.twiceyuan.retrokv.KVStorage;
import com.twiceyuan.retrokv.KeyValue;
import com.twiceyuan.retrokv.annotations.KeyName;

import java.util.Set;

/**
 * Created by twiceYuan on 20/01/2017.
 *
 * Mock setting items
 */
public interface AnnotationSettings extends KVStorage {

    @KeyName("launch_count")
    KeyValue<Integer> launchCount();

    @KeyName("is_login")
    KeyValue<Boolean> isLogin();

    @KeyName("user_points")
    KeyValue<Float> userPoints();

    @KeyName("last_login")
    KeyValue<Long> lastLogin();

    @KeyName("username")
    KeyValue<String> username();

    @KeyName("user_tags")
    KeyValue<Set<String>> userTags();
}
