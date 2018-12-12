package com.storiqa.storiqawallet.ui.splash

import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.network.errors.DialogButton
import com.storiqa.storiqawallet.network.errors.ErrorPresenterDialog
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
        //getNavigator()?.openRegistrationActivity()
        showErrorDialog(ErrorPresenterDialog(R.string.error_email_not_exist, R.string.password, R.drawable.general_error_icon, DialogButton(R.string.button_ok, {})))
    }
}