package com.storiqa.storiqawallet.ui.login

import com.storiqa.storiqawallet.screen_main.MainActivity
import com.storiqa.storiqawallet.screen_register.RegisterActivity
import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.password.PasswordRecoveryActivity

class LoginNavigator(private val navigator: INavigator) : ILoginNavigator {

    override fun openRegistrationActivity() {
        navigator.startActivity(RegisterActivity::class.java, false)
    }

    override fun openPasswordRecoveryActivity() {
        navigator.startActivity(PasswordRecoveryActivity::class.java, false)
    }

    override fun openMainActivity() {
        navigator.startActivity(MainActivity::class.java, true)
    }

}