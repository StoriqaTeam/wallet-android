package com.storiqa.storiqawallet.ui.splash

import com.storiqa.storiqawallet.screen_pin_code_enter.EnterPinCodeActivity
import com.storiqa.storiqawallet.screen_register.RegisterActivity
import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.login.LoginActivity

class SplashNavigator(private val navigator: INavigator) : ISplashNavigator {

    override fun openLoginActivity() {
        navigator.startActivity(LoginActivity::class.java, false)
    }

    override fun openRegistrationActivity() {
        navigator.startActivity(RegisterActivity::class.java, false)
    }

    override fun openEnterPinActivity() {
        navigator.startActivity(EnterPinCodeActivity::class.java, true)
    }

}