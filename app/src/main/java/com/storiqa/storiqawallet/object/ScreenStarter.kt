package com.storiqa.storiqawallet.`object`

import android.content.Context
import android.content.Intent
import com.storiqa.storiqawallet.login_screen.LoginActivity

class ScreenStarter {

    fun startLoginScreen(context : Context) {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }
}