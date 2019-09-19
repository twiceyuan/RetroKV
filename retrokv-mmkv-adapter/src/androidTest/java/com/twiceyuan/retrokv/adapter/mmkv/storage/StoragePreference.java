package com.twiceyuan.retrokv.adapter.mmkv.storage;

import com.twiceyuan.retrokv.KVStorage;
import com.twiceyuan.retrokv.Preference;

/**
 * Created by twiceYuan on 10/02/2017.
 * <p>
 * KVStorage Preference
 */
public interface StoragePreference extends KVStorage {

    Preference<String> username();

    Preference<String> password();
}
