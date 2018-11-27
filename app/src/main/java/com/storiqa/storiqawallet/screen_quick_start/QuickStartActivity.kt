package com.storiqa.storiqawallet.screen_quick_start

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.db.PreferencesHelper
import com.storiqa.storiqawallet.objects.ScreenStarter
import kotlinx.android.synthetic.main.activity_quick_start.*

class QuickStartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_start)

        val helper = PreferencesHelper(this)

        btnSetQuickStart.setOnClickListener {
            if (!helper.isPinCodeEnabled()) {
                ScreenStarter().startEnterPinCodeScreen(this)
            } else if (!helper.isFingerprintEnabled()) {
                ScreenStarter().startFingerScanScreen(this)
            }
        }

        btnDoNotUse.setOnClickListener { ScreenStarter().startMainScreen(this) }
    }
}
