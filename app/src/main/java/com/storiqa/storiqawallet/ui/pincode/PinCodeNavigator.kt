package com.storiqa.storiqawallet.ui.pincode

import android.content.Intent
import com.storiqa.storiqawallet.ui.authorization.AuthorizationActivity
import com.storiqa.storiqawallet.ui.base.navigator.BaseNavigator
import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.main.MainActivity

class PinCodeNavigator(private val navigator: INavigator) : BaseNavigator(navigator),
        IPinCodeNavigator {

    override fun openMainActivity() {
        navigator.startActivity(MainActivity::class.java, Intent.FLAG_ACTIVITY_CLEAR_TASK, Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun openLoginActivity() {
        navigator.startActivity(AuthorizationActivity::class.java)
    }
}