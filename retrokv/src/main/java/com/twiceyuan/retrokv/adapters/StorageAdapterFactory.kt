package com.twiceyuan.retrokv.adapters

interface StorageAdapterFactory<T: StorageAdapter> {

    fun create(instanceName: String): T
}