package com.storiqa.storiqawallet.common

import androidx.lifecycle.MutableLiveData

class NonNullMutableLiveData<T>(value: T) : MutableLiveData<T>() {

    init {
        setValue(value)
    }

    override fun getValue(): T = super.getValue()!!

    @Suppress("RedundantOverride")
    override fun setValue(value: T) = super.setValue(value)
}