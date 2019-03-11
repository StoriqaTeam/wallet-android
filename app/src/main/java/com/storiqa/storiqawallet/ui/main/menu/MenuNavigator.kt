package com.storiqa.storiqawallet.ui.main.menu

import android.util.Log
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.ui.base.navigator.BaseNavigator
import com.storiqa.storiqawallet.ui.base.navigator.IBaseNavigator
import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.main.change_password.PasswordChangeFragment
import com.storiqa.storiqawallet.ui.main.profile.EditProfileFragment


interface IMenuNavigator : IBaseNavigator {
    fun showEditProfile()
    fun showChangePassword()
    fun showAppInfo()
}


class MenuNavigator(private val navigator: INavigator) : BaseNavigator(navigator), IMenuNavigator {

    override fun showEditProfile() {
        navigator.replaceFragment(R.id.container, EditProfileFragment(), "edit_profile", "edit_profile")
    }

    override fun showChangePassword() {
        navigator.replaceFragment(R.id.container, PasswordChangeFragment(), "password_change", "password_change")
    }

    override fun showAppInfo() {
        Log.d("MenuNavigator", "showAppInfo")
    }

}
