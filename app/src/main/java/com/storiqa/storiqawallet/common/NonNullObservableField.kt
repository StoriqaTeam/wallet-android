package com.storiqa.storiqawallet.common

import androidx.databinding.Observable
import androidx.databinding.ObservableField

class NonNullObservableField<T>(value: T, vararg dependencies: Observable) :
        ObservableField<T>(*dependencies) {

    init {
        set(value)
    }

    override fun get(): T = super.get()!!

    @Suppress("RedundantOverride")
    override fun set(value: T) = super.set(value)
}
