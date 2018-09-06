package com.storiqa.storiqawallet.screen_scan_finger

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField

class ScanFingerViewModel : ViewModel() {

    val isAuthSucced = ObservableField<Boolean>(false)
    val isAuthFailed = ObservableField<Boolean>(false)

    fun startListenForFingerprint() {
        ScanFingerModel().startListenForFingerprint({
            isAuthSucced.set(true)
            isAuthFailed.set(false)
        }, {
            isAuthSucced.set(false)
            isAuthFailed.set(true)
        })
    }
}