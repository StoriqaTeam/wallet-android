package com.storiqa.storiqawallet.ui.splash

import android.arch.lifecycle.ViewModel
import java.lang.ref.WeakReference

class SplashViewModel : ViewModel() {

    private var refNavigator: WeakReference<SplashNavigator>? = null

    fun setNavigator(splashNavigator: SplashNavigator) {
        refNavigator = WeakReference(splashNavigator)
    }

    fun checkLoggedIn() {

    }

    fun startLogin() {
        refNavigator?.get()?.openLoginActivity()
    }

    fun startRegistration() {
        refNavigator?.get()?.openRegistrationActivity()
    }
}