package com.storiqa.storiqawallet.screen_pin_code_enter

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class EnterPinViewModelFactory(private val view: EnterPinCodeView) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EnterPinViewModel(view) as T
    }

}