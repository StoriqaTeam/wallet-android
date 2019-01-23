package com.storiqa.storiqawallet.ui.main.menu

import com.storiqa.storiqawallet.ui.base.BaseViewModel
import javax.inject.Inject

interface IMenuViewModel {
    fun onEditProfileSelected()
    fun onChangePasswordSelected()
    fun onAppInfoSelected()
}


class MenuViewModel
@Inject

constructor(navigator: IMenuNavigator) : BaseViewModel<IMenuNavigator>(), IMenuViewModel {

    init {
        setNavigator(navigator)
    }

    override fun onEditProfileSelected() {
        getNavigator()?.showEditProfile()
    }

    override fun onChangePasswordSelected() {
        getNavigator()?.showChangePassword()
    }

    override fun onAppInfoSelected() {
        getNavigator()?.showAppInfo()
    }

}