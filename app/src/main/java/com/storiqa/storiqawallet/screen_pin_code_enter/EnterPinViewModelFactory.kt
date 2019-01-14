package com.storiqa.storiqawallet.screen_pin_code_enter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EnterPinViewModelFactory(private val view: EnterPinCodeView) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EnterPinViewModel(view) as T
    }

}