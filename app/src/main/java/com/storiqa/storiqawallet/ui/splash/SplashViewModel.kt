package com.storiqa.storiqawallet.ui.splash

import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import java.util.*
import javax.inject.Inject

class SplashViewModel
@Inject
constructor(private val appData: IAppDataStorage) : BaseViewModel<ISplashNavigator>() {

    fun checkLoggedIn() {
        if (appData.isFirstLaunch) {
            appData.deviceId = UUID.randomUUID().toString()
            appData.isFirstLaunch = false
        }

        if (appData.isPinEntered) {
            getNavigator()?.openPinCodeActivity()
            getNavigator()?.finishActivity()
        }
    }

    fun startLogin() {
        getNavigator()?.openLoginActivity()
        getNavigator()?.finishActivity()
    }

    fun startRegistration() {
        getNavigator()?.openRegistrationActivity()
        getNavigator()?.finishActivity()
    }
}