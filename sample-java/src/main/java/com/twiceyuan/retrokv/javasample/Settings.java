package com.twiceyuan.retrokv.javasample;

import com.twiceyuan.retrokv.KVStorage;
import com.twiceyuan.retrokv.Preference;

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
    Preference<Integer> launchCount();

    /**
     * Save current user instance
     */
    Preference<User> currentUser();

    /**
     * Map type test
     */
    Preference<HashMap<String, Integer>> testMap();
}
