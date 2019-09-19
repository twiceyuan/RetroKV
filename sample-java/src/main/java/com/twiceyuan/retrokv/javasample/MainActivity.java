package com.twiceyuan.retrokv.javasample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.twiceyuan.retrokv.KeyValue;
import com.twiceyuan.retrokv.RetroKV;
import com.twiceyuan.retrokv.adapter.mmkv.MmkvAdapterFactory;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = new RetroKV.Builder()
                .setAdapterFactory(new MmkvAdapterFactory(this))
                .build()
                .createInstance(Settings.class);

        // Get preference item holder
        KeyValue<Integer> launchCount = settings.launchCount();

        // getWithDefault the preference value
        Integer count = launchCount.get();
        count = count == null ? 1 : count;

        ((TextView) findViewById(R.id.tv_launch)).setText(String.format("启动次数：%s", count));

        // set the preference value
        launchCount.set(count + 1);

        // Object store
        KeyValue<User> userPreference = settings.currentUser();

        User defaultUser = new User("twiceYuan", "123456", 0f, 1000);
        User currentUser = userPreference.get();
        currentUser = currentUser == null ? defaultUser : currentUser;

        currentUser.age++;
        currentUser.score -= 0.1f;

        ((TextView) findViewById(R.id.tv_user)).setText(String.format("当前用户：\n%s", currentUser.toString()));

        userPreference.set(currentUser);

        HashMap<String, Integer> map = new HashMap<>();
        map.put("Test", 123);
        settings.testMap().set(map);

        HashMap<String, Integer> readMap = settings.testMap().get();
        if (readMap != null) {
            Log.i(TAG, String.valueOf(readMap.get("Test")));
        }
    }

    public void clear(View view) {
        settings.clear();
    }
}
