package com.storiqa.storiqawallet.objects

import android.content.Context
import android.content.Intent
import com.storiqa.storiqawallet.screen_login.LoginActivity
import com.storiqa.storiqawallet.screen_register.RegisterActivity

class ScreenStarter {

    fun startLoginScreen(context : Context) {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }

    fun startRegisterScreen(context : Context) {
        context.startActivity(Intent(context, RegisterActivity::class.java))
    }
}