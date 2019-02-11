package com.storiqa.storiqawallet.ui.main.receive

import com.storiqa.storiqawallet.data.network.WalletApi
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import javax.inject.Inject

class ReceiveViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val walletApi: WalletApi) : BaseViewModel<IMainNavigator>() {

    init {
        setNavigator(navigator)
    }

}