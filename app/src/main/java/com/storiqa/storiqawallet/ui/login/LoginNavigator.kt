package com.storiqa.storiqawallet.ui.login

import com.storiqa.storiqawallet.screen_main.MainActivity
import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.password.PasswordRecoveryActivity
import com.storiqa.storiqawallet.ui.registration.RegistrationActivity

class LoginNavigator(private val navigator: INavigator) : ILoginNavigator {

    override fun openRegistrationActivity() {
        navigator.startActivity(RegistrationActivity::class.java)
    }

    override fun openPasswordRecoveryActivity() {
        navigator.startActivity(PasswordRecoveryActivity::class.java)
    }

    override fun openMainActivity() {
        navigator.startActivity(MainActivity::class.java)
    }

}