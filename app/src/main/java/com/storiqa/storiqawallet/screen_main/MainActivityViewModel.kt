package com.storiqa.storiqawallet.screen_main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.storiqa.storiqawallet.enums.Screen
import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.objects.Transaction
import com.storiqa.storiqawallet.repositories.BillsRepository
import com.storiqa.storiqawallet.repositories.TransactionRepository

class MainActivityViewModel : ViewModel() {

    val transactions = MutableLiveData<Array<Transaction>>()
    val selectedScreen = MutableLiveData<Screen>()
    val bills = MutableLiveData<Array<Bill>>()
    val selectedBillId = MutableLiveData<String>()
    var goBack : () -> Unit = {}

    fun openMyWalletScreen() {
        BillsRepository().getBills().subscribe { loadedBills ->
            bills.value = loadedBills
            selectedScreen.value = Screen.MY_WALLET
        }
    }

    fun updateTransactionList(idOfSelectedBill : String, limit : Int? = null) {
        TransactionRepository().getTransactions(idOfSelectedBill, limit).subscribe { newTransactions ->
            transactions.value = newTransactions
        }
    }

    fun selectScreen(screen : Screen) {
        selectedScreen.value = screen
    }
}