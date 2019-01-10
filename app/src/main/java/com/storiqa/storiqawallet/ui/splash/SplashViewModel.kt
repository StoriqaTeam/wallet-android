package com.storiqa.storiqawallet.ui.splash

import com.storiqa.storiqawallet.data.IAppDataStorage
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class SplashViewModel
@Inject
constructor(navigator: ISplashNavigator,
            private val appData: IAppDataStorage) : BaseViewModel<ISplashNavigator>() {

    init {
        setNavigator(navigator)
    }

    fun checkLoggedIn() {
        if (appData.isFirstLaunch) {
            appData.deviceId = UUID.randomUUID().toString()
            appData.isFirstLaunch = false
        }

        if (appData.isPinEntered)
            getNavigator()?.openPinCodeActivity()
    }

    fun startLogin() {
        getNavigator()?.openLoginActivity()
    }

    fun startRegistration() {
        getNavigator()?.openRegistrationActivity()
    }
}