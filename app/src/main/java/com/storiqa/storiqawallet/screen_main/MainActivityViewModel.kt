package com.storiqa.storiqawallet.screen_main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.storiqa.storiqawallet.enums.Screen
import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.objects.Transaction
import com.storiqa.storiqawallet.repositories.BillsRepository
import com.storiqa.storiqawallet.repositories.TransactionRepository

class MainActivityViewModel : ViewModel() {

    val selectedScreen = MutableLiveData<Screen>()
    val bills = MutableLiveData<Array<Bill>>()
    val selectedBillId = MutableLiveData<String>()

    fun openMyWalletScreen() {
        BillsRepository().getBills().subscribe { loadedBills ->
            bills.value = loadedBills
            selectedScreen.value = Screen.MY_WALLET
        }
    }

    val transactions = MutableLiveData<Array<Transaction>>()

    fun updateTransactionList(idOfSelectedBill : String) {
        TransactionRepository().getTransactions(idOfSelectedBill, 10).subscribe({ newTransactions ->
            transactions.value = newTransactions
        }, {
            transactions.value = arrayOf()
        })
    }

    fun openDepositScreen() {
        selectedScreen.value = Screen.DEPOSIT
    }

    fun openExchangeScreen() {
        selectedScreen.value = Screen.EXCHANGE
    }

    fun openSendScreen() {
        selectedScreen.value = Screen.SEND
    }

    fun openMenuScreen() {
        selectedScreen.value = Screen.MENU
    }
}