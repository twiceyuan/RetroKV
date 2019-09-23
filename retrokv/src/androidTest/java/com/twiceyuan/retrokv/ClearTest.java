package com.twiceyuan.retrokv;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;

import com.twiceyuan.retrokv.adapters.SharedPreferencesAdapterFactory;
import com.twiceyuan.retrokv.storage.KeyValueStorage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by twiceYuan on 10/02/2017.
 *
 * 测试 clear 功能
 */
public class ClearTest {

    private Context mAppContext;

    @Before
    public void useAppContext() {
        // Context of the app under test.
        mAppContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void testClear() {
        KeyValueStorage preference = new RetroKV.Builder()
                .setAdapterFactory(new SharedPreferencesAdapterFactory(mAppContext))
                .build()
                .createInstance(KeyValueStorage.class);

        preference.username().set("twiceYuan");
        preference.password().set("password");

        Assert.assertEquals("twiceYuan", preference.username().get());
        Assert.assertEquals("password", preference.password().get());

        preference.clear();

        Assert.assertNull(preference.username().get());
        Assert.assertNull(preference.username().get());
    }
}
