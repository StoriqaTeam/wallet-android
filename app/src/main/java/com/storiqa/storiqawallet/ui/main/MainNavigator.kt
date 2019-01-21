package com.storiqa.storiqawallet.ui.main

import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.main.exchange.ExchangeFragment
import com.storiqa.storiqawallet.ui.main.menu.MenuFragment
import com.storiqa.storiqawallet.ui.main.receive.ReceiveFragment
import com.storiqa.storiqawallet.ui.main.send.SendFragment
import com.storiqa.storiqawallet.ui.main.wallet.WalletFragment

class MainNavigator(private val navigator: INavigator) : IMainNavigator {

    private val containerId = R.id.container

    override fun showWalletFragment() {
        navigator.replaceFragment(containerId, WalletFragment(), "wallet")
    }

    override fun showSendFragment() {
        navigator.replaceFragment(containerId, SendFragment(), "send")
    }

    override fun showExchangeFragment() {
        navigator.replaceFragment(containerId, ExchangeFragment(), "exchange")
    }

    override fun showReceiveFragment() {
        navigator.replaceFragment(containerId, ReceiveFragment(), "receive")
    }

    override fun showMenuFragment() {
        navigator.replaceFragment(containerId, MenuFragment(), "menu")
    }

}