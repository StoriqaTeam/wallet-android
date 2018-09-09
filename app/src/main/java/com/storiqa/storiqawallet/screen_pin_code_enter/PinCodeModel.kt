package com.storiqa.storiqawallet.screen_pin_code_enter

import com.storiqa.storiqawallet.StoriqaApp
import com.storiqa.storiqawallet.db.PreferencesHelper

class PinCodeModel {
    fun savePincode(pincode: String) {
        PreferencesHelper(StoriqaApp.context).savePinCode(pincode)
    }

    fun onPinCodeSetted() {
        PreferencesHelper(StoriqaApp.context).setPinCodeEnabled(true)
    }

    fun pinCodeIsValid(pincode: String): Boolean {
        return PreferencesHelper(StoriqaApp.context).getPinCode().equals(pincode)
    }

}