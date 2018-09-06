package com.storiqa.storiqawallet.screen_pincode_setup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.objects.FingerprintHepler
import com.storiqa.storiqawallet.objects.ScreenStarter
import kotlinx.android.synthetic.main.activity_pin_code_setup.*

class PinCodeSetupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_code_setup)

        btnSetUpPinCode.setOnClickListener { ScreenStarter().startEnterPinCodeScreen(this) }
        btnDoNotUse.setOnClickListener {
            if(FingerprintHepler(this@PinCodeSetupActivity).isFingerprintSetupNotAvailable()) {
                ScreenStarter().startMainScreen(this@PinCodeSetupActivity)
            } else {
                ScreenStarter().startFingerprintSetupScreen(this@PinCodeSetupActivity)
            }
        }
    }
}
