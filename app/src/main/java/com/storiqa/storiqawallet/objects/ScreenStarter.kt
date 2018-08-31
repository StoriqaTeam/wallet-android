package com.storiqa.storiqawallet.objects

import android.content.Context
import android.content.Intent
import com.storiqa.storiqawallet.constants.Extras
import com.storiqa.storiqawallet.screen_login.LoginActivity
import com.storiqa.storiqawallet.screen_recover_password.screen_recover_email_enter_first_step.RecoverPasswordActivity
import com.storiqa.storiqawallet.screen_recover_password.screen_recover_password_enter_second_step.NewPasswordEnterActivity
import com.storiqa.storiqawallet.screen_register.RegisterActivity

class ScreenStarter {

    fun startLoginScreen(context: Context) = context.startActivity(Intent(context, LoginActivity::class.java))

    fun startRegisterScreen(context: Context) = context.startActivity(Intent(context, RegisterActivity::class.java))

    fun startRecoverPasswordScreen(context: Context) = context.startActivity(Intent(context, RecoverPasswordActivity::class.java))

    fun startNewPasswordEnterScreen(context: Context, email: String) = context.startActivity(Intent(context, NewPasswordEnterActivity::class.java).putExtra(Extras().email, email))
}