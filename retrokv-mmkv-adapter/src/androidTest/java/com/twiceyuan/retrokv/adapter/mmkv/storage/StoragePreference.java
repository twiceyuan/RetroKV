package com.twiceyuan.retrokv.adapter.mmkv.storage;

import com.twiceyuan.retrokv.KVStorage;
import com.twiceyuan.retrokv.KeyValue;

/**
 * Created by twiceYuan on 10/02/2017.
 * <p>
 * KVStorage KeyValue
 */
public interface StoragePreference extends KVStorage {

    KeyValue<String> username();

    KeyValue<String> password();
}
