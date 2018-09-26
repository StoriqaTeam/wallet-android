package com.storiqa.storiqawallet.screen_quick_start

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.db.PreferencesHelper
import com.storiqa.storiqawallet.objects.ScreenStarter
import com.storiqa.storiqawallet.screen_pin_code_enter.PinCodeModel
import com.storiqa.storiqawallet.screen_scan_finger.FingerprintModel
import kotlinx.android.synthetic.main.activity_quick_start.*

class QuickStartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_start)

        val helper = PreferencesHelper(this)

        btnSetQuickStart.setOnClickListener {
            if(!helper.isPinCodeEnabled()) {
                ScreenStarter().startEnterPinCodeScreen(this)
            } else if(!helper.isFingerprintEnabled()) {
                ScreenStarter().startFingerScanScreen(this)
            }
        }

        btnDoNotUse.setOnClickListener { ScreenStarter().startMainScreen(this) }
    }
}
