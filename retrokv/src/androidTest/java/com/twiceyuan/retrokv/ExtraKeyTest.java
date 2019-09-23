package com.twiceyuan.retrokv;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.twiceyuan.retrokv.adapters.SharedPreferencesAdapterFactory;
import com.twiceyuan.retrokv.annotations.KeyParam;
import com.twiceyuan.retrokv.storage.KeyValueStorage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by twiceYuan on 10/02/2017.
 * <p>
 * 测试 clear 功能
 */
public class ExtraKeyTest {

    public interface ExtraKeyConfig extends KeyValueStorage {

        KeyValue<String> userToken(@KeyParam("userId") String userId);
    }

    private ExtraKeyConfig config;

    @Before
    public void useAppContext() {
        // Context of the app under test.
        Context mAppContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        config = new RetroKV.Builder()
                .setAdapterFactory(new SharedPreferencesAdapterFactory(mAppContext))
                .build()
                .createInstance("extra_keys", ExtraKeyConfig.class);
    }

    @Test
    public void useCase() {
        final String userId1 = "twiceYuan";
        final String userToken1 = "twiceYuan's Token";
        final String userId2 = "Tonia";
        final String userToken2 = "Tonia's Token";

        config.userToken(userId1).set(userToken1);
        config.userToken(userId2).set(userToken2);

        Assert.assertEquals(userToken1, config.userToken(userId1).get());
        Assert.assertEquals(userToken2, config.userToken(userId2).get());
    }

    @After
    public void cleanup() {
        config.clear();
    }
}
