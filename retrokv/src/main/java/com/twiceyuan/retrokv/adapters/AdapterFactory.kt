package com.twiceyuan.retrokv.adapters

interface AdapterFactory<T: Adapter> {

    fun create(instanceName: String): T
}