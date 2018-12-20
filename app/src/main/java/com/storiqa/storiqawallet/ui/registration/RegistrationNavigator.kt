package com.storiqa.storiqawallet.ui.registration

import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.login.LoginActivity

class RegistrationNavigator(private val navigator: INavigator) : IRegistrationNavigator {

    override fun openLoginActivity() {
        navigator.startActivity(LoginActivity::class.java, false)
    }
}