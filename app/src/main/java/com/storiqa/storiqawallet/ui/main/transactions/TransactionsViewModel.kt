package com.storiqa.storiqawallet.ui.main.transactions

import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import javax.inject.Inject

class TransactionsViewModel
@Inject
constructor(navigator: IMainNavigator) : BaseViewModel<IMainNavigator>() {

    init {
        setNavigator(navigator)
    }

}