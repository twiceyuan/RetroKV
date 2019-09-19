package com.twiceyuan.retrokv.javasample;

import com.twiceyuan.retrokv.KVStorage;
import com.twiceyuan.retrokv.KeyValue;

import java.util.HashMap;

/**
 * Created by twiceYuan on 2017/5/19.
 *
 * Settings
 */
public interface Settings extends KVStorage {

    /**
     * Mark launch count
     */
    KeyValue<Integer> launchCount();

    /**
     * Save current user instance
     */
    KeyValue<User> currentUser();

    /**
     * Map type test
     */
    KeyValue<HashMap<String, Integer>> testMap();
}
