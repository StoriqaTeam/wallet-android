package com.storiqa.storiqawallet.ui.password

import com.storiqa.storiqawallet.ui.authorization.AuthorizationActivity
import com.storiqa.storiqawallet.ui.base.navigator.INavigator

class PasswordRecoveryNavigator(private val navigator: INavigator) : IPasswordRecoveryNavigator {

    override fun closeActivity() {
        navigator.finishActivity()
    }

    override fun openLoginActivity() {
        navigator.startActivity(AuthorizationActivity::class.java)
    }
}