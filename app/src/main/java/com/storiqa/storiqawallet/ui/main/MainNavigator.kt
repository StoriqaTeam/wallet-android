package com.storiqa.storiqawallet.ui.main

import android.os.Bundle
import android.view.View
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.ui.base.navigator.BaseNavigator
import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.dialogs.exchange.ExchangeConfirmationDialog
import com.storiqa.storiqawallet.ui.dialogs.send.SendConfirmationDialog
import com.storiqa.storiqawallet.ui.main.account.AccountFragment
import com.storiqa.storiqawallet.ui.main.details.TransactionDetailsFragment
import com.storiqa.storiqawallet.ui.main.exchange.ExchangeFragment
import com.storiqa.storiqawallet.ui.main.menu.MenuFragment
import com.storiqa.storiqawallet.ui.main.receive.ReceiveFragment
import com.storiqa.storiqawallet.ui.main.send.SendFragment
import com.storiqa.storiqawallet.ui.main.transactions.TransactionsFragment
import com.storiqa.storiqawallet.ui.main.wallet.WalletFragment

class MainNavigator(private val navigator: INavigator) : BaseNavigator(navigator), IMainNavigator {

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

    override fun showAccountFragment(bundle: Bundle, element: View, transition: String) {
        val fragment = AccountFragment()
        fragment.arguments = bundle
        navigator.replaceFragmentAndAddToBackStack(containerId, fragment, "account", "account")
    }

    override fun showTransactionsFragment(accountAddress: String) {
        val fragment = TransactionsFragment()
        val bundle = Bundle()
        bundle.putString(TransactionsFragment.KEY_ADDRESS, accountAddress)
        fragment.arguments = bundle
        navigator.replaceFragmentAndAddToBackStack(containerId, fragment, "transactions", "transactions")
    }

    override fun showTransactionDetailsFragment(address: String, transactionId: String) {
        val fragment = TransactionDetailsFragment()
        val bundle = Bundle()
        bundle.putString(TransactionDetailsFragment.KEY_ADDRESS, address)
        bundle.putString(TransactionDetailsFragment.KEY_TRANSACTION_ID, transactionId)
        fragment.arguments = bundle
        navigator.replaceFragmentAndAddToBackStack(containerId, fragment, "details", "details")
    }

    override fun showSendConfirmationDialog(address: String, amount: String, fee: String, total: String, onConfirm: () -> Unit) {
        val dialog = SendConfirmationDialog.newInstance(address, amount, fee, total)
        dialog.setConfirmClickedListener(onConfirm)
        navigator.showDialogFragment(dialog)
    }

    override fun showExchangeConfirmationDialog(
            remittanceAccount: String,
            remittanceAmount: String,
            collectionAccount: String,
            collectionAmount: String,
            onConfirm: () -> Unit
    ) {
        val dialog = ExchangeConfirmationDialog.newInstance(
                remittanceAccount,
                remittanceAmount,
                collectionAccount,
                collectionAmount)
        dialog.setConfirmClickedListener(onConfirm)
        navigator.showDialogFragment(dialog)
    }
}