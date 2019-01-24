package com.storiqa.storiqawallet.ui.main

import com.storiqa.storiqawallet.ui.base.navigator.IBaseNavigator

interface IMainNavigator : IBaseNavigator {

    fun showWalletFragment()

    fun showSendFragment()

    fun showExchangeFragment()

    fun showReceiveFragment()

    fun showMenuFragment()

}