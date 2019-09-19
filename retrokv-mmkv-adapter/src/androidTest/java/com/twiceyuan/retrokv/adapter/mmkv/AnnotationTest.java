package com.twiceyuan.retrokv.adapter.mmkv;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.tencent.mmkv.MMKV;
import com.twiceyuan.retrokv.RetroKV;
import com.twiceyuan.retrokv.adapter.mmkv.storage.AnnotationSettings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * 注解测试
 */
@RunWith(AndroidJUnit4.class)
public class AnnotationTest {

    private AnnotationSettings mSettings;

    private Context mAppContext;

    private MMKV mmkv;

    @Before
    public void useAppContext() {
        // Context of the app under test.
        mAppContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        mSettings = new RetroKV.Builder()
                .setAdapterFactory(new MmkvAdapterFactory(mAppContext))
                .build()
                .createInstance(AnnotationSettings.class);

        mmkv = MMKV.mmkvWithID(AnnotationSettings.class.getSimpleName());
    }

    /**
     * 测试 RetroKV 保存，使用系统方法读取
     */
    @Test
    public void testWriteWithSystemRead() {
        mSettings.username().set("twiceYuan");
        String stored = mmkv.decodeString("username");
        Assert.assertEquals("twiceYuan", stored);
        mSettings.username().remove();
    }

    /**
     * 测试 RetroKV 读取使用系统方法保存的值
     */
    @Test
    public void testReadWithSystemWrite() {
        mmkv.encode("username", "twiceYuan");
        String storedUsername = mSettings.username().get();
        Assert.assertEquals("twiceYuan", storedUsername);
        mSettings.username().remove();
    }

    /**
     * 测试 Integer 读写
     */
    @Test
    public void testInteger() {
        mSettings.launchCount().set(7);
        Integer stored = mSettings.launchCount().get();
        Assert.assertEquals((Integer) 7, stored);
        mSettings.launchCount().remove();
    }

    /**
     * 测试 Boolean 读写
     */
    @Test
    public void testBoolean() {
        mSettings.isLogin().set(true);
        Boolean isLogin = mSettings.isLogin().get();
        Assert.assertEquals(isLogin, true);
        mSettings.isLogin().remove();
    }

    /**
     * 测试 Float 读写
     */
    @Test
    public void testFloat() {
        mSettings.userPoints().set(1.1f);
        Float userPoints = mSettings.userPoints().get();
        Assert.assertEquals((Float) 1.1f, userPoints);
        mSettings.userPoints().remove();
    }

    /**
     * 测试 Long 读写
     */
    @Test
    public void testLong() {
        mSettings.lastLogin().set(12378217381L);
        Assert.assertEquals(12378217381L, mSettings.lastLogin().get(), 0);
        mSettings.lastLogin().remove();
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
        mSettings.userTags().set(originSet);
        Set<String> storedSet = mSettings.userTags().get();

        Object[] originArray = originSet.toArray();
        Object[] storedArray = storedSet.toArray();

        Arrays.sort(originArray);
        Arrays.sort(storedArray);

        Assert.assertArrayEquals(originArray, storedArray);
        mSettings.userTags().remove();
    }
}
