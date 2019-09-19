package com.twiceyuan.retrokv

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.twiceyuan.retrokv.adapters.SharedPreferencesAdapterFactory
import com.twiceyuan.retrokv.storage.StoragePreference
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 测试多个 instance 是否冲突
 */
@RunWith(AndroidJUnit4::class)
class MultipleInstanceTest {

    private var retroKV: RetroKV? = null
    private var settings1: Settings1? = null
    private var settings2: Settings2? = null

    @Before
    fun useAppContext() {
        // Context of the app under test.
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        retroKV = RetroKV.Builder()
                .setAdapterFactory(SharedPreferencesAdapterFactory(context))
                .build()
    }

    interface Settings1: StoragePreference {
        fun value1(): KeyValue<String>
    }

    interface Settings2: StoragePreference {
        fun value1(): KeyValue<String>
    }

    @Test
    fun testCase() {

        settings1 = retroKV?.create()
        settings2 = retroKV?.create()

        settings1?.value1()?.set("test")
        Assert.assertEquals(settings1?.value1()?.get(), "test")
        Assert.assertEquals(settings2?.value1()?.get(), null)

        settings2?.value1()?.set("test2")
        Assert.assertEquals(settings1?.value1()?.get(), "test")
        Assert.assertEquals(settings2?.value1()?.get(), "test2")
    }

    @After
    fun cleanup() {
        settings1?.clear()
        settings2?.clear()
    }
}