package com.storiqa.storiqawallet.ui.splash

import com.storiqa.storiqawallet.ui.base.navigator.IBaseNavigator

interface ISplashNavigator : IBaseNavigator {

    fun openLoginActivity()

    fun openRegistrationActivity()

    fun openPinCodeActivity()
}