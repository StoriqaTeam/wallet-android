package com.storiqa.storiqawallet.ui.main.menu

import com.storiqa.storiqawallet.ui.base.BaseViewModel
import javax.inject.Inject

interface IMenuViewModel {
    fun didSelectEditProfile()
    fun didSelectChangePassword()
    fun didSelectAppInfo()
}


class MenuViewModel
@Inject

constructor(navigator: IMenuNavigator) : BaseViewModel<IMenuNavigator>(), IMenuViewModel {

    init {
        setNavigator(navigator)
    }

    override fun didSelectEditProfile() {
        getNavigator()?.showEditProfile()
    }

    override fun didSelectChangePassword() {
        getNavigator()?.showChangePassword()
    }

    override fun didSelectAppInfo() {
        getNavigator()?.showAppInfo()
    }

}