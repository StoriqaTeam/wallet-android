package com.storiqa.storiqawallet.ui.splash

import android.support.v4.app.FragmentActivity
import com.storiqa.storiqawallet.screen_pin_code_enter.EnterPinCodeActivity
import com.storiqa.storiqawallet.screen_register.RegisterActivity
import com.storiqa.storiqawallet.ui.base.navigator.Navigator
import com.storiqa.storiqawallet.ui.login.LoginActivity

class SplashNavigator(activity: FragmentActivity) : Navigator(activity), ISplashNavigator {

    override fun openLoginActivity() {
        startActivity(LoginActivity::class.java, false)
    }

    override fun openRegistrationActivity() {
        startActivity(RegisterActivity::class.java, false)
    }

    override fun openEnterPinActivity() {
        startActivity(EnterPinCodeActivity::class.java, true)
    }

}