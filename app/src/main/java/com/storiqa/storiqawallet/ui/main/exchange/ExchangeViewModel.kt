package com.storiqa.storiqawallet.ui.main.exchange

import com.storiqa.storiqawallet.data.network.WalletApi
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import javax.inject.Inject

class ExchangeViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val walletApi: WalletApi) : BaseViewModel<IMainNavigator>() {

    init {
        setNavigator(navigator)
    }

}