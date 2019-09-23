package com.twiceyuan.retrokv.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.twiceyuan.retrokv.RetroKV
import com.twiceyuan.retrokv.adapter.mmkv.MmkvAdapterFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settings = RetroKV.Builder()
                .setAdapterFactory(MmkvAdapterFactory(this))
                .build()
                .create()

        btn_save.setOnClickListener {
            saveInt()
            saveString()
            saveUser()
        }

        btn_read.setOnClickListener {

            for (key in settings.allKeys()) {

                val content = "" +
                        "sampleInt: ${settings.sampleInt().get()}\n" +
                        "sampleString: ${settings.sampleString().get()}\n" +
                        "sampleInt: ${settings.sampleParcelable().get()}\n"

                et_read_all.setText(content)
            }
        }

        btn_clear_all.setOnClickListener { settings.clear() }
    }

    private fun saveInt() {
        val originInput = et_for_int.text.toString()
        if (originInput.isEmpty()) return
        try {
            val intValue = Integer.parseInt(originInput)
            settings.sampleInt().set(intValue)
        } catch (e: Exception) {
            Toast.makeText(this, R.string.format_error_int, Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveString() {
        settings.sampleString().set(et_for_string.text.toString())
    }

    private fun saveUser() {
        val ageInput = et_for_parcelable_user_age.text.toString()
        val ageValue: Int
        try {
            ageValue = Integer.parseInt(ageInput)
        } catch (e: Exception) {
            Toast.makeText(this, R.string.format_error_int, Toast.LENGTH_SHORT).show()
            return
        }
        val user = User(et_for_parcelable_username.text.toString(), ageValue)
        settings.sampleParcelable().set(user)
    }
}
