package com.storiqa.storiqawallet.ui.main

import com.storiqa.storiqawallet.data.IAppDataStorage
import com.storiqa.storiqawallet.data.polling.IShortPolling
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val appData: IAppDataStorage,
            private val shortPolling: IShortPolling) : BaseViewModel<IMainNavigator>() {

    init {
        setNavigator(navigator)
        //shortPolling.start(193, "d.kruglov@storiqa.com")
    }

    fun onMenuItemSelected(position: Int, wasSelected: Boolean): Boolean {
        if (!wasSelected)
            when (position) {
                0 -> getNavigator()?.showWalletFragment()
                1 -> getNavigator()?.showSendFragment()
                2 -> getNavigator()?.showExchangeFragment()
                3 -> getNavigator()?.showReceiveFragment()
                4 -> getNavigator()?.showMenuFragment()
            }

        return true
    }

}