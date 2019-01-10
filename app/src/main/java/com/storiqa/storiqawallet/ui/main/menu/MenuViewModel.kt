package com.storiqa.storiqawallet.ui.main.menu

import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import javax.inject.Inject

class MenuViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val walletApi: WalletApi) : BaseViewModel<IMainNavigator>() {

    init {
        setNavigator(navigator)
    }

}