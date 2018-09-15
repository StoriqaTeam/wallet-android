package com.storiqa.storiqawallet.screen_main.my_wallet

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.storiqa.storiqawallet.objects.Transaction
import com.storiqa.storiqawallet.repositories.TransactionRepository

class WalletTransactionsViewModel : ViewModel() {

    val transactions = MutableLiveData<Array<Transaction>>()

    fun updateTransactionList() {
        TransactionRepository().getTransactions(10).subscribe({newTransactions ->
            transactions.value = newTransactions
        }, {
            transactions.value = arrayOf()
        })
    }

}