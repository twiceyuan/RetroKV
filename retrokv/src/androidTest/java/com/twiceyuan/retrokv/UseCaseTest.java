package com.twiceyuan.retrokv;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.twiceyuan.retrokv.adapters.SharedPreferencesAdapterFactory;
import com.twiceyuan.retrokv.storage.Settings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * 基本用例测试
 */
@RunWith(AndroidJUnit4.class)
public class UseCaseTest {

    private Settings mSettings;
    private Context  mAppContext;

    @Before
    public void useAppContext() {
        // Context of the app under test.
        mAppContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        mSettings = new RetroKV.Builder()
                .setAdapterFactory(new SharedPreferencesAdapterFactory(mAppContext))
                .build()
                .createInstance(Settings.class);
    }

    /**
     * 测试 RetroKV 保存，使用系统方法读取
     */
    @Test
    public void testWriteWithSystemRead() {
        mSettings.username().set("twiceYuan");
        SharedPreferences preferences = mAppContext.getSharedPreferences(
                Settings.class.getSimpleName(),
                Context.MODE_PRIVATE);
        String stored = preferences.getString("username", "");
        Assert.assertEquals("twiceYuan", stored);
        mSettings.username().remove();
    }

    /**
     * 测试 RetroKV 读取使用系统方法保存的值
     */
    @Test
    public void testReadWithSystemWrite() {
        SharedPreferences preferences = mAppContext.getSharedPreferences(
                Settings.class.getSimpleName(),
                Context.MODE_PRIVATE);
        preferences.edit().putString("username", "twiceYuan").apply();

        String storedUsername = mSettings.username().get();
        Assert.assertEquals("twiceYuan", storedUsername);
        mSettings.username().remove();
    }

    /**
     * 测试 Integer 读写
     */
    @Test
    public void testInteger() {
        mSettings.launch_count().set(7);
        Integer stored = mSettings.launch_count().get();
        Assert.assertEquals((Integer) 7, stored);
        mSettings.launch_count().remove();
    }

    /**
     * 测试 Boolean 读写
     */
    @Test
    public void testBoolean() {
        mSettings.is_login().set(true);
        Boolean isLogin = mSettings.is_login().get();
        Assert.assertEquals(isLogin, true);
        mSettings.is_login().remove();
    }

    /**
     * 测试 Float 读写
     */
    @Test
    public void testFloat() {
        mSettings.user_points().set(1.1f);
        Float value = mSettings.user_points().get();
        value = value == null ? 0 : value;
        Assert.assertEquals(1.1f, value, 0);
        mSettings.user_points().remove();
    }

    /**
     * 测试 Long 读写
     */
    @Test
    public void testLong() {
        mSettings.last_login().set(12378217381L);
        Long value = mSettings.last_login().get();
        value = value == null ? 0 : value;
        Assert.assertEquals(12378217381L, value, 0);
        mSettings.last_login().remove();
    }

    /**
     * 测试 String 读写
     */
    @Test
    public void testString() {
        mSettings.username().set("twiceYuan");
        Assert.assertEquals("twiceYuan", mSettings.username().get());
        mSettings.username().remove();
    }

    /**
     * 测试 StringSet 读写
     */
    @Test
    public void testStringSet() {
        Set<String> originSet = new TreeSet<>();
        originSet.add("Hello");
        originSet.add("World");
        originSet.add("Blog");
        originSet.add("Singularity");
        mSettings.user_tags().set(originSet);
        Set<String> storedSet = mSettings.user_tags().get();

        Object[] originArray = originSet.toArray();
        Object[] storedArray = storedSet == null ? new Object[0] : storedSet.toArray();

        Arrays.sort(originArray);
        Arrays.sort(storedArray);

        Assert.assertArrayEquals(originArray, storedArray);
        mSettings.user_tags().remove();
    }
}
