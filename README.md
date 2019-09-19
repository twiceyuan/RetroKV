# RetroKV

[![](https://jitpack.io/v/twiceyuan/RetroRV.svg)](https://jitpack.io/#twiceyuan/RetroRV)

KV wrapper inspired by Retrofit，类型安全的 KV 工具。

* [x] 类型安全
* [x] Key 强一致
* [x] 支持存储方案的更换（目前支持 SharedPreferences 和 MMKV）

RetroKV 和通常的 KV 库相当于 Retrofit 和 OkHttp 的关系，RetroKV 只是为了让定义和使用方便和保证一致性。

如果你想让 RetroRV 支持更多 K-V 方案，可以参照 module retrokv-mmkv-adapter 的实现方式开发新的适配器。

TODO

* [ ] 代理类定义合法性预检查

### 使用示范

定义

```java
// 定义需要存放 KV 的配置类，需要继承 KVStorage 接口
public interface Settings extends KVStorage {

    Preference<Integer> launch_count();

    Preference<Boolean> is_login();

    Preference<Float> user_points();

    Preference<Long> last_login();

    Preference<String> username();

    Preference<Set<String>> user_tags();
}
```

Save or read:

```java
// 建议在 Application onCreate 时创建唯一实例。
Settings settings = new RetroKV.Builder()
    // 使用 MMKV 作为底层存储方案
    .setAdapterFactory(new MmkvAdapterFactory(this))
    .build()
    .createInstance(Settings.class);

// 存取
settings.currentUser().set(testUser);
User user = settings.currentUser().get();
```

## 引用

[![](https://jitpack.io/v/twiceyuan/RetroKV.svg)](https://jitpack.io/#twiceyuan/RetroKV)


```groovy
// project
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

// module
dependencies {
    // TODO
}
```

## License

```
Copyright 2019 twiceYuan.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
