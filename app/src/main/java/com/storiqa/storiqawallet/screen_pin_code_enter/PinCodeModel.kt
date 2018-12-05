package com.storiqa.storiqawallet.screen_pin_code_enter

import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.db.PreferencesHelper

class PinCodeModel {
    fun savePincode(pincode: String) {
        PreferencesHelper(App.context).savePinCode(pincode)
    }

    fun onPinCodeSetted() {
        PreferencesHelper(App.context).setPinCodeEnabled(true)
    }

    fun pinCodeIsValid(pincode: String): Boolean {
        return PreferencesHelper(App.context).getPinCode().equals(pincode)
    }

    fun eraseUserQuickLaunch() {
        PreferencesHelper(App.context).setFingerprintEnabled(false)
        PreferencesHelper(App.context).setPinCodeEnabled(false)
        PreferencesHelper(App.context).setQuickLaunchFinished(false)
    }

}