package com.twiceyuan.retrokv.adapter.mmkv.storage;

import com.twiceyuan.retrokv.KVStorage;
import com.twiceyuan.retrokv.Preference;
import com.twiceyuan.retrokv.annotations.KeyName;

import java.util.Set;

/**
 * Created by twiceYuan on 20/01/2017.
 *
 * Mock setting items
 */
public interface AnnotationSettings extends KVStorage {

    @KeyName("launch_count")
    Preference<Integer> launchCount();

    @KeyName("is_login")
    Preference<Boolean> isLogin();

    @KeyName("user_points")
    Preference<Float> userPoints();

    @KeyName("last_login")
    Preference<Long> lastLogin();

    @KeyName("username")
    Preference<String> username();

    @KeyName("user_tags")
    Preference<Set<String>> userTags();
}
