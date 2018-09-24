package com.storiqa.storiqawallet.screen_quick_start

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.objects.ScreenStarter
import kotlinx.android.synthetic.main.activity_quick_start.*

class QuickStartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_start)

        btnSetQuickStart.setOnClickListener { ScreenStarter().startEnterPinCodeScreen(this) }
        btnDoNotUse.setOnClickListener { ScreenStarter().startMainScreen(this) }
    }
}
