package com.storiqa.storiqawallet.screen_pin_code_enter

import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.db.PreferencesHelper

class PinCodeModel {
    fun savePincode(pincode: String) {
        PreferencesHelper(App.instance).savePinCode(pincode)
    }

    fun onPinCodeSetted() {
        PreferencesHelper(App.instance).setPinCodeEnabled(true)
    }

    fun pinCodeIsValid(pincode: String): Boolean {
        return PreferencesHelper(App.instance).getPinCode().equals(pincode)
    }

    fun eraseUserQuickLaunch() {
        PreferencesHelper(App.instance).setFingerprintEnabled(false)
        PreferencesHelper(App.instance).setPinCodeEnabled(false)
        PreferencesHelper(App.instance).setQuickLaunchFinished(false)
    }

}