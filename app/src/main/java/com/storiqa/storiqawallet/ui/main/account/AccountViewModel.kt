package com.storiqa.storiqawallet.ui.main.account

import com.storiqa.storiqawallet.common.CurrencyConverter
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.data.IUserDataStorage
import com.storiqa.storiqawallet.data.db.entity.AccountEntity
import com.storiqa.storiqawallet.data.db.entity.RateEntity
import com.storiqa.storiqawallet.data.mapper.AccountMapper
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.data.model.Transaction
import com.storiqa.storiqawallet.data.repository.IAccountsRepository
import com.storiqa.storiqawallet.data.repository.IRatesRepository
import com.storiqa.storiqawallet.data.repository.ITransactionsRepository
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class AccountViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val accountsRepository: IAccountsRepository,
            private val ratesRepository: IRatesRepository,
            private val transactionsRepository: ITransactionsRepository,
            private val userData: IUserDataStorage) : BaseViewModel<IMainNavigator>() {

    val updateAccounts = SingleLiveEvent<ArrayList<Account>>()
    val updateTransactions = SingleLiveEvent<List<Transaction>>()

    var currentPosition = 0

    var cards: ArrayList<Account> = ArrayList()

    private var accounts: List<AccountEntity> = ArrayList()
    private var rates: List<RateEntity> = ArrayList()
    private var transactions: List<Transaction> = ArrayList()

    private var transactionsSubscription: Disposable? = null

    init {
        setNavigator(navigator)

        ratesRepository.getRates()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    rates = it
                    updateAccounts()
                }

        accountsRepository.getAccounts(userData.id)
                .observeOn(AndroidSchedulers.mainThread())
                .filter { !it.isEmpty() }
                .subscribe {
                    accounts = it.reversed()
                    updateAccounts()
                }
    }

    fun onSeeAllButtonClicked() {
        getNavigator()?.showTransactionsFragment()
    }

    fun onAccountSelected(position: Int) {
        currentPosition = position
        val sab = transactionsSubscription
        if (sab != null && !sab.isDisposed)
            sab.dispose()

        transactionsSubscription = transactionsRepository.getTransactionsByAddress(cards[position].accountAddress, 10).observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    transactions = it
                    updateTransactions()
                }
    }

    private fun updateAccounts() {
        val mapper = AccountMapper(CurrencyConverter(rates))
        if (accounts.isNotEmpty() && rates.isNotEmpty()) {
            cards = ArrayList()
            accounts.forEach { cards.add(mapper.map(it)) }
            updateAccounts.value = cards
        }
    }

    private fun updateTransactions() {
        updateTransactions.value = transactions
    }
}