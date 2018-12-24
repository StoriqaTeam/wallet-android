package com.storiqa.storiqawallet.ui.pincode

import com.storiqa.storiqawallet.screen_main.MainActivity
import com.storiqa.storiqawallet.ui.base.navigator.INavigator

class PinCodeNavigator(private val navigator: INavigator) : IPinCodeNavigator {

    override fun openMainActivity() {
        navigator.startActivity(MainActivity::class.java)
    }

}