package com.storiqa.storiqawallet.register_screen

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.storiqa.storiqawallet.R

class RegisterActivity : MvpAppCompatActivity(), RegisterView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
}
