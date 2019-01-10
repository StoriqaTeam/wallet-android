package com.storiqa.storiqawallet.ui.splash

import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.login.LoginActivity
import com.storiqa.storiqawallet.ui.registration.RegistrationActivity

class SplashNavigator(private val navigator: INavigator) : ISplashNavigator {

    override fun openLoginActivity() {
        navigator.startActivity(LoginActivity::class.java)
    }

    override fun openRegistrationActivity() {
        navigator.startActivity(RegistrationActivity::class.java)
    }

    override fun openPinCodeActivity() {
        navigator.startActivity("com.storiqa.storiqawallet.ENTER_PIN")
    }

}