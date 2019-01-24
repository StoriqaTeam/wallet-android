package com.storiqa.storiqawallet.ui.main.menu

import android.util.Log
import com.storiqa.storiqawallet.ui.base.navigator.IBaseNavigator
import com.storiqa.storiqawallet.ui.base.navigator.INavigator


interface IMenuNavigator : IBaseNavigator {
    fun showEditProfile()
    fun showChangePassword()
    fun showAppInfo()
}


class MenuNavigator(private val navigator: INavigator) : IMenuNavigator {

    override fun showEditProfile() {
        Log.d("MenuNavigator", "showEditProfile")
    }

    override fun showChangePassword() {
        Log.d("MenuNavigator", "showChangePassword")
    }

    override fun showAppInfo() {
        Log.d("MenuNavigator", "showAppInfo")
    }

}
