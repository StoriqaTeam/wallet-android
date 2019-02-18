package com.storiqa.storiqawallet.ui.splash

import com.storiqa.storiqawallet.ui.authorization.AuthorizationActivity
import com.storiqa.storiqawallet.ui.base.navigator.BaseNavigator
import com.storiqa.storiqawallet.ui.base.navigator.INavigator

class SplashNavigator(private val navigator: INavigator) : BaseNavigator(navigator), ISplashNavigator {

    override fun openLoginActivity() {
        navigator.startActivity(AuthorizationActivity::class.java)
    }

    override fun openRegistrationActivity() {
        navigator.startActivity("com.storiqa.storiqawallet.SIGN_UP")
    }

    override fun openPinCodeActivity() {
        navigator.startActivity("com.storiqa.storiqawallet.ENTER_PIN")
    }

}