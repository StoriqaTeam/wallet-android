package com.storiqa.storiqawallet.ui.main

import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.main.exchange.ExchangeFragment
import com.storiqa.storiqawallet.ui.main.menu.MenuFragment
import com.storiqa.storiqawallet.ui.main.receive.ReceiveFragment
import com.storiqa.storiqawallet.ui.main.send.SendFragment
import com.storiqa.storiqawallet.ui.main.wallet.WalletFragment

class MainNavigator(private val navigator: INavigator) : IMainNavigator {

    override fun showWalletFragment() {
        navigator.replaceFragment(R.id.container, WalletFragment(), "wallet")
    }

    override fun showSendFragment() {
        navigator.replaceFragment(R.id.container, SendFragment(), "send")
    }

    override fun showExchangeFragment() {
        navigator.replaceFragment(R.id.container, ExchangeFragment(), "exchange")
    }

    override fun showReceiveFragment() {
        navigator.replaceFragment(R.id.container, ReceiveFragment(), "receive")
    }

    override fun showMenuFragment() {
        navigator.replaceFragment(R.id.container, MenuFragment(), "menu")
    }

}