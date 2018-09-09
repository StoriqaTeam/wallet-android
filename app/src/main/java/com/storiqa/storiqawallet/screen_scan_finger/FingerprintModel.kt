package com.storiqa.storiqawallet.screen_scan_finger

import com.storiqa.storiqawallet.StoriqaApp
import com.storiqa.storiqawallet.db.PreferencesHelper

class FingerprintModel {

    fun onFingerprintEnabled() {
        PreferencesHelper(StoriqaApp.context).setFingerprintEnabled(true)
    }

}