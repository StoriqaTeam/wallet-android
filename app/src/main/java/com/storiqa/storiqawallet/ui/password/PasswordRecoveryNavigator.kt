package com.storiqa.storiqawallet.ui.password

import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.login.LoginActivity

class PasswordRecoveryNavigator(private val navigator: INavigator) : IPasswordRecoveryNavigator {

    override fun closeActivity() {
        navigator.finishActivity()
    }

    override fun openLoginActivity() {
        navigator.startActivity(LoginActivity::class.java)
    }
}