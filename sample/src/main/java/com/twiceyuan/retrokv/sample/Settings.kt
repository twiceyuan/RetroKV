package com.twiceyuan.retrokv.sample

import com.twiceyuan.retrokv.KVStorage
import com.twiceyuan.retrokv.Preference

/**
 * Created by twiceYuan on 20/01/2017.

 * Mock Settings
 */
interface Settings : KVStorage {

    /**
     * Mark launch count
     */
    fun launchCount(): Preference<Int>

    /**
     * Save current user instance
     */
    fun currentUser(): Preference<User>
}
