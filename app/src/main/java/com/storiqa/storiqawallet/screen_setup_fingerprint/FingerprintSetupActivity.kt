package com.storiqa.storiqawallet.screen_setup_fingerprint

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.objects.ScreenStarter
import kotlinx.android.synthetic.main.activity_fingerprint_setup.*

class FingerprintSetupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fingerprint_setup)

        btnSetUpFingerprint.setOnClickListener { ScreenStarter().startFingerScanScreen(this) }
        btnDoNotUse.setOnClickListener { ScreenStarter().startMainScreen(this) }
    }
}
