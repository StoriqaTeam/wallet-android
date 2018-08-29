package com.storiqa.storiqawallet.pin_code_enter_screen

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.arellomobile.mvp.MvpView
import com.storiqa.storiqawallet.R

class EnterPinCodeActivity : AppCompatActivity(), MvpView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_pin_code)
    }
}
