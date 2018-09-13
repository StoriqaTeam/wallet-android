package com.storiqa.storiqawallet.screen_pin_code_enter

import com.storiqa.storiqawallet.enums.PinCodeEnterType

interface EnterPinCodeView {
    fun enterType(): PinCodeEnterType
    fun vibrate()
    fun redirectOnMainScreen()
    fun redirectToFingerPrintSetup()
}