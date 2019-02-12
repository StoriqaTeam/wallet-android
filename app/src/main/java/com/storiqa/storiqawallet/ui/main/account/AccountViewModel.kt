package com.storiqa.storiqawallet.ui.main.account

import androidx.databinding.ObservableBoolean
import com.storiqa.storiqawallet.common.CurrencyConverter
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.data.db.entity.AccountEntity
import com.storiqa.storiqawallet.data.db.entity.RateEntity
import com.storiqa.storiqawallet.data.mapper.AccountMapper
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.data.model.Transaction
import com.storiqa.storiqawallet.data.preferences.IUserDataStorage
import com.storiqa.storiqawallet.data.repository.IAccountsRepository
import com.storiqa.storiqawallet.data.repository.IRatesRepository
import com.storiqa.storiqawallet.data.repository.ITransactionsRepository
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.common.NoTransactionsViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AccountViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val accountsRepository: IAccountsRepository,
            private val ratesRepository: IRatesRepository,
            private val transactionsRepository: ITransactionsRepository,
            private val userData: IUserDataStorage
) : BaseViewModel<IMainNavigator>(), NoTransactionsViewModel {

    private val lastTransactionsAmount = 10

    override val isNoTransactions = ObservableBoolean(false)
    val isShowButtonAvailable = ObservableBoolean(false)

    val updateAccounts = SingleLiveEvent<List<Account>>()
    val updateTransactions = SingleLiveEvent<List<Transaction>>()

    var currentPosition = 0

    var cards: ArrayList<Account> = ArrayList()
    var transactions: List<Transaction> = ArrayList()

    private var transactionsSubscription: Disposable? = null

    init {
        setNavigator(navigator)

        Flowable.combineLatest(ratesRepository.getRates(),
                accountsRepository.getAccounts(userData.id),
                BiFunction(::mapAccounts))
                .distinct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { updateAccounts.value = it }
    }

    fun onSeeAllButtonClicked() {
        getNavigator()?.showTransactionsFragment(cards[currentPosition].accountAddress)
    }

    fun onTransactionClicked(position: Int) {
        getNavigator()?.showTransactionDetailsFragment(cards[currentPosition].accountAddress, transactions[position].id)
    }

    fun onAccountSelected(position: Int) {
        currentPosition = position
        /*val sab = transactionsSubscription
        if (sab != null && !sab.isDisposed)
            sab.dispose()*/

        val address = cards[position].accountAddress
        transactionsSubscription = transactionsRepository
                .getTransactionsByAddress(address, lastTransactionsAmount)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    transactions = it
                    updateTransactions()
                }
    }

    private fun mapAccounts(rates: List<RateEntity>, accounts: List<AccountEntity>): List<Account> {
        val mapper = AccountMapper(CurrencyConverter(rates))
        if (accounts.isNotEmpty() && rates.isNotEmpty()) {
            cards = ArrayList()
            accounts.reversed().forEach { cards.add(mapper.map(it)) }
        }
        return cards
    }

    private fun updateTransactions() {
        isNoTransactions.set(transactions.isEmpty())
        isShowButtonAvailable.set(!transactions.isEmpty())
        updateTransactions.value = transactions
    }
}