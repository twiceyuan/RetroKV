package com.twiceyuan.retrokv.sample

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by twiceYuan on 09/02/2017.
 *
 *
 * 用户对象
 */
@Parcelize
data class User(
        val username: String,
        var password: String,
        var score: Float,
        var age: Int
): Parcelable {

    override fun toString(): String {
        return String.format("" +
                "{\n" +
                "    username: %s,\n" +
                "    password: %s,\n" +
                "    score: %s,\n" +
                "    age: %s\n" +
                "}",
                username, password, score, age)
    }
}
