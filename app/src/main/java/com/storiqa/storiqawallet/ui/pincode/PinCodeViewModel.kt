package com.storiqa.storiqawallet.ui.pincode

import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import javax.inject.Inject

class PinCodeViewModel
@Inject
constructor(navigator: IPinCodeNavigator,
            private val walletApi: WalletApi) :
        BaseViewModel<IPinCodeNavigator>() {

}