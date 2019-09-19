package com.twiceyuan.retrokv.adapter.mmkv.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MockUser(
        val username: String,
        val age: Int
): Parcelable