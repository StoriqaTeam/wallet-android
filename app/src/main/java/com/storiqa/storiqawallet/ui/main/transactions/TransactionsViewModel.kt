package com.storiqa.storiqawallet.ui.main.transactions

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.data.model.Transaction
import com.storiqa.storiqawallet.data.repository.ITransactionsRepository
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class TransactionsViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val transactionsRepository: ITransactionsRepository) : BaseViewModel<IMainNavigator>() {

    val updateTransactions = SingleLiveEvent<List<Transaction>>()

    var transactionsAll: List<Transaction>? = null
    var transactionsSend = ArrayList<Transaction>()
    var transactionsReceive = ArrayList<Transaction>()

    var currentPosition = 0

    private lateinit var address: String

    init {
        setNavigator(navigator)
    }

    @SuppressLint("CheckResult")
    fun loadTransactions(address: String) {
        this.address = address
        transactionsRepository
                .getAllTransactionsByAddress(address)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    transactionsAll = it
                    it.forEach { transaction ->
                        if (transaction.toAccount.blockchainAddress == address)
                            transactionsReceive.add(transaction)
                        else
                            transactionsSend.add(transaction)
                    }
                    updateTransactions(currentPosition)
                }
    }

    fun onTransactionClicked(position: Int) {
        val transactionId = when (currentPosition) {
            0 -> transactionsAll?.get(position)?.id!!
            1 -> transactionsSend[position].id
            2 -> transactionsReceive[position].id
            else -> throw Throwable("Transaction not found")
        }
        getNavigator()?.showTransactionDetailsFragment(address, transactionId)
    }

    fun updateTransactions(position: Int) {
        currentPosition = position
        when (position) {
            0 -> updateTransactions.value = transactionsAll
            1 -> updateTransactions.value = transactionsSend
            2 -> updateTransactions.value = transactionsReceive
        }
    }

}