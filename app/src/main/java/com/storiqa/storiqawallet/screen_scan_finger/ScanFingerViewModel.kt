package com.storiqa.storiqawallet.screen_scan_finger

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class ScanFingerViewModel : ViewModel() {
    val model = FingerprintModel()
    val isAuthSucced = ObservableField<Boolean>(false)
    val isAuthFailed = ObservableField<Boolean>(false)

    fun startListenForFingerprint() {
        isAuthFailed.set(false)
        ScanFingerModel().startListenForFingerprint({
            isAuthSucced.set(true)
            isAuthFailed.set(false)
            model.onFingerprintEnabled()
        }, {
            isAuthSucced.set(false)
            isAuthFailed.set(true)
        })
    }
}