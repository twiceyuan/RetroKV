package com.twiceyuan.retrokv.storage;

import com.twiceyuan.retrokv.KVStorage;
import com.twiceyuan.retrokv.KeyValue;

import java.util.Set;

/**
 * Created by twiceYuan on 20/01/2017.
 *
 * Mock setting items
 */
public interface Settings extends KVStorage {

    KeyValue<Integer> launch_count();

    KeyValue<Boolean> is_login();

    KeyValue<Float> user_points();

    KeyValue<Long> last_login();

    KeyValue<String> username();

    KeyValue<Set<String>> user_tags();
}
