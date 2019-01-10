package com.storiqa.storiqawallet.ui.pincode

import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.login.LoginActivity
import com.storiqa.storiqawallet.ui.main.MainActivity

class PinCodeNavigator(private val navigator: INavigator) : IPinCodeNavigator {

    override fun openMainActivity() {
        navigator.startActivity(MainActivity::class.java)
    }

    override fun openLoginActivity() {
        navigator.startActivity(LoginActivity::class.java)
    }

    override fun closeActivity() {
        navigator.finishActivity()
    }
}