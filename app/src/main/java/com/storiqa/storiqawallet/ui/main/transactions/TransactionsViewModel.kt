package com.storiqa.storiqawallet.ui.main.transactions

import android.annotation.SuppressLint
import androidx.databinding.ObservableBoolean
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.data.model.Transaction
import com.storiqa.storiqawallet.data.repository.ITransactionsRepository
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.common.NoTransactionsViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class TransactionsViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val transactionsRepository: ITransactionsRepository
) : BaseViewModel<IMainNavigator>(), NoTransactionsViewModel {

    private val tabAll = 0
    private val tabSent = 1
    private val tabReceived = 2

    override val isNoTransactions = ObservableBoolean(false)
    val updateTransactions = SingleLiveEvent<List<Transaction>>()

    var transactionsAll: List<Transaction> = ArrayList()
    var transactionsSend = ArrayList<Transaction>()
    var transactionsReceive = ArrayList<Transaction>()

    var currentTabPosition = tabAll

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
                    updateTransactions(currentTabPosition)
                }
    }

    fun getTransactions(): List<Transaction> {
        return when (currentTabPosition) {
            tabAll -> transactionsAll
            tabSent -> transactionsSend
            tabReceived -> transactionsReceive
            else -> throw Throwable("Transaction not found")
        }
    }

    fun onTransactionClicked(position: Int) {
        val transactionId = when (currentTabPosition) {
            tabAll -> transactionsAll[position].id
            tabSent -> transactionsSend[position].id
            tabReceived -> transactionsReceive[position].id
            else -> throw Throwable("Transaction not found")
        }
        getNavigator()?.showTransactionDetailsFragment(address, transactionId)
    }

    fun updateTransactions(position: Int) {
        currentTabPosition = position
        when (position) {
            tabAll -> {
                updateTransactions.value = transactionsAll
                isNoTransactions.set(transactionsAll.isEmpty())
            }
            tabSent -> {
                updateTransactions.value = transactionsSend
                isNoTransactions.set(transactionsSend.isEmpty())
            }
            tabReceived -> {
                updateTransactions.value = transactionsReceive
                isNoTransactions.set(transactionsReceive.isEmpty())
            }
        }
    }

}