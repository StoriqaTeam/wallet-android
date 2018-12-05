package com.storiqa.storiqawallet.screen_scan_finger

import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.db.PreferencesHelper

class FingerprintModel {

    fun onFingerprintEnabled() {
        PreferencesHelper(App.context).setFingerprintEnabled(true)
    }

}