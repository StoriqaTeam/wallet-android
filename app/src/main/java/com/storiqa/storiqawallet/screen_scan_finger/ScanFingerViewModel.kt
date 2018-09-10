package com.storiqa.storiqawallet.screen_scan_finger

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField

class ScanFingerViewModel : ViewModel() {
    val model = FingerprintModel()
    val isAuthSucced = ObservableField<Boolean>(false)
    val isAuthFailed = ObservableField<Boolean>(false)

    fun startListenForFingerprint() {
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