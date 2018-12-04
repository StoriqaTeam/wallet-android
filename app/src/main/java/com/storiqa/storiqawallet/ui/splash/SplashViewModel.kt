package com.storiqa.storiqawallet.ui.splash

import android.arch.lifecycle.ViewModel
import java.lang.ref.WeakReference

class SplashViewModel : ViewModel() {

    var refNavigator: WeakReference<SplashNavigator>? = null

    fun checkLoggedIn() {

    }

    fun startLogin() {
        refNavigator?.get()?.openLoginActivity()
    }

    fun startRegistration() {
        refNavigator?.get()?.openRegistrationActivity()
    }

    fun setNavigator(splashNavigator: SplashNavigator) {
        refNavigator = WeakReference(splashNavigator)
    }
}