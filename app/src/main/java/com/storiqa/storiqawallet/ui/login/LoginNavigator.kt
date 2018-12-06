package com.storiqa.storiqawallet.ui.login

import android.support.v4.app.FragmentActivity
import com.storiqa.storiqawallet.screen_main.MainActivity
import com.storiqa.storiqawallet.screen_register.RegisterActivity
import com.storiqa.storiqawallet.ui.base.navigator.Navigator
import com.storiqa.storiqawallet.ui.password.PasswordRecoveryActivity

class LoginNavigator(activity: FragmentActivity) : Navigator(activity), ILoginNavigator {

    override fun openRegistrationActivity() {
        startActivity(RegisterActivity::class.java, false)
    }

    override fun openPasswordRecoveryActivity() {
        startActivity(PasswordRecoveryActivity::class.java, false)
    }

    override fun openMainActivity() {
        startActivity(MainActivity::class.java, true)
    }

}