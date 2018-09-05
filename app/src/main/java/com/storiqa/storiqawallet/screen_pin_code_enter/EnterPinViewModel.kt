package com.storiqa.storiqawallet.screen_pin_code_enter

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.storiqa.storiqawallet.enums.PinCodeEnterType

class EnterPinViewModel(var pinCodeEnterType : PinCodeEnterType) : ViewModel() {

    var pinCode = ObservableField<String>("")

    fun enterDigit(digit: String) = pinCode.set(pinCode.get() + digit)

    fun eraseLastDigit() {
        pinCode.set(pinCode.get()!!.substring(0, pinCode.get()!!.lastIndex))
    }
}