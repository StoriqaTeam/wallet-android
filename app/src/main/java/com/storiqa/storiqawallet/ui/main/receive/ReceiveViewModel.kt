package com.storiqa.storiqawallet.ui.main.receive

import android.util.Log
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import javax.inject.Inject

class ReceiveViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val walletApi: WalletApi) : BaseViewModel<IMainNavigator>() {

    init {
        setNavigator(navigator)

        Log.d("viewModel", "Init view model")
    }

}