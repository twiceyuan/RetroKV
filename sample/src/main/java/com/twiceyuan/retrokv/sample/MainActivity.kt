package com.twiceyuan.retrokv.sample

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.twiceyuan.retrokv.RetroKV
import com.twiceyuan.retrokv.adapter.mmkv.MmkvAdapterFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settings: Settings = RetroKV.Builder()
                .setAdapterFactory(MmkvAdapterFactory(this))
                .build()
                .create()

        // Get preference item holder
        val launchCount = settings.launchCount()

        // getWithDefault the preference value
        val count: Int? = launchCount.get() ?: 0

        findViewById<TextView>(R.id.tv_launch).text = String.format("启动次数：%s", count)

        if (count != null) {
            // set the preference value
            launchCount.set(count.plus(1))
        } else {
            launchCount.set(1)
        }

        // Object store
        val userPreference = settings.currentUser()
        var currentUser: User? = userPreference.get()
        if (currentUser != null) {
            currentUser.age++
            currentUser.score -= 0.1f
            val textDesc = "当前用户：\n$currentUser"
            findViewById<TextView>(R.id.tv_user).text = textDesc
        } else {
            currentUser = User(username = "twiceYuan", password = "123456", age = 0, score = 1000f)
        }
        userPreference.set(currentUser)

        btn_clear_all.setOnClickListener { settings.clear() }
    }
}
