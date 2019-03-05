package com.storiqa.storiqawallet.ui.main.account

import androidx.databinding.ObservableBoolean
import com.storiqa.storiqawallet.common.NonNullMutableLiveData
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.data.model.Transaction
import com.storiqa.storiqawallet.data.preferences.IUserDataStorage
import com.storiqa.storiqawallet.data.repository.IAccountsRepository
import com.storiqa.storiqawallet.data.repository.ITransactionsRepository
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.common.NoTransactionsViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class AccountViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val accountsRepository: IAccountsRepository,
            private val transactionsRepository: ITransactionsRepository,
            private val userData: IUserDataStorage
) : BaseViewModel<IMainNavigator>(), NoTransactionsViewModel {

    private val lastTransactionsAmount = 10

    override val isNoTransactions = ObservableBoolean(false)
    val isShowButtonAvailable = ObservableBoolean(false)

    val updateTransactions = SingleLiveEvent<List<Transaction>>()

    var currentPosition = 0

    var accounts = NonNullMutableLiveData<List<Account>>(ArrayList())
    var transactions: List<Transaction> = ArrayList()

    private var transactionsSubscription: Disposable? = null

    init {
        setNavigator(navigator)

        accountsRepository
                .getAccounts(userData.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { accounts.value = it.reversed() }
    }

    fun onSeeAllButtonClicked() {
        getNavigator()?.showTransactionsFragment(accounts.value[currentPosition].accountAddress)
    }

    fun onTransactionClicked(position: Int) {
        getNavigator()?.showTransactionDetailsFragment(accounts.value[currentPosition].accountAddress, transactions[position].id)
    }

    fun onAccountSelected(position: Int) {
        currentPosition = position
        accountsRepository.currentAccountPosition = position

        val address = accounts.value[position].accountAddress
        transactionsSubscription = transactionsRepository
                .getTransactionsByAddress(address, lastTransactionsAmount)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    transactions = it
                    updateTransactions()
                }
    }

    private fun updateTransactions() {
        isNoTransactions.set(transactions.isEmpty())
        isShowButtonAvailable.set(!transactions.isEmpty())
        updateTransactions.value = transactions
    }
}