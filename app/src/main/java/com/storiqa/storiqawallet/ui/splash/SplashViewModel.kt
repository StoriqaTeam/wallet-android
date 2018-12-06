package com.storiqa.storiqawallet.ui.splash

import com.storiqa.storiqawallet.ui.base.BaseViewModel
import javax.inject.Inject

class SplashViewModel
@Inject
constructor(navigator: ISplashNavigator) : BaseViewModel<ISplashNavigator>() {

    init {
        setNavigator(navigator)
    }

    fun checkLoggedIn() {
        //TODO open pin code screen
    }

    fun startLogin() {
        getNavigator()?.openLoginActivity()
    }

    fun startRegistration() {
        getNavigator()?.openRegistrationActivity()
    }
}