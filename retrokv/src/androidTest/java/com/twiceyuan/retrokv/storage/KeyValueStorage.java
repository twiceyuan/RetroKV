package com.twiceyuan.retrokv.storage;

import com.twiceyuan.retrokv.KVStorage;
import com.twiceyuan.retrokv.KeyValue;

/**
 * Created by twiceYuan on 10/02/2017.
 * <p>
 * KVStorage KeyValue
 */
public interface KeyValueStorage extends KVStorage {

    KeyValue<String> username();

    KeyValue<String> password();
}
