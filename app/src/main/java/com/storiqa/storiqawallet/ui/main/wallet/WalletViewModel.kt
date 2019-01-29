package com.storiqa.storiqawallet.ui.main.wallet

import android.os.Bundle
import android.view.View
import com.storiqa.storiqawallet.common.CurrencyConverter
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.data.IAppDataStorage
import com.storiqa.storiqawallet.data.ITokenProvider
import com.storiqa.storiqawallet.data.IUserDataStorage
import com.storiqa.storiqawallet.data.db.AppDatabase
import com.storiqa.storiqawallet.data.db.entity.*
import com.storiqa.storiqawallet.data.mapper.AccountMapper
import com.storiqa.storiqawallet.data.model.Card
import com.storiqa.storiqawallet.data.polling.ShortPolling
import com.storiqa.storiqawallet.data.repository.IAccountsRepository
import com.storiqa.storiqawallet.data.repository.IRatesRepository
import com.storiqa.storiqawallet.data.repository.IUserRepository
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.network.responses.TransactionResponse
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import com.storiqa.storiqawallet.utils.SignUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class WalletViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val walletApi: WalletApi,
            private val signUtil: SignUtil,
            private val appDatabase: AppDatabase,
            private val userRepository: IUserRepository,
            private val accountsRepository: IAccountsRepository,
            private val ratesRepository: IRatesRepository,
            private val userData: IUserDataStorage,
            private val appData: IAppDataStorage,
            private val tokenProvider: ITokenProvider) : BaseViewModel<IMainNavigator>() {

    val updateAccounts = SingleLiveEvent<ArrayList<Card>>()

    var cards: ArrayList<Card> = ArrayList()

    private var accounts: List<AccountEntity> = ArrayList()
    private var rates: List<RateEntity> = ArrayList()

    init {
        setNavigator(navigator)

        val token = appData.token
        if (tokenProvider.isExpired(token))
            tokenProvider.refreshToken({
                appData.token = token
                updateData()
            }, ::handleError)

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

        ShortPolling(accountsRepository, ratesRepository).start(userData.id, userData.email)

        val email = appData.currentUserEmail
        val signHeader = signUtil.createSignHeader(email)
        walletApi.getTransactions(userData.id, signHeader.timestamp, signHeader.deviceId,
                signHeader.signature, "Bearer $token", 0, 100).subscribe({
            saveTransactions(it)
            print("rrrr")
        }, {
            print("error")
        })
    }

    private fun saveTransactions(transactions: List<TransactionResponse>) {
        val transactionDao = appDatabase.transactionDao()
        val transactionAccountDao = appDatabase.transactionAccountDao()
        val transactionAccountJoinDao = appDatabase.transactionAccountJoinDao()
        val blockchainIdDao = appDatabase.blockchainIdDao()
        for (transaction in transactions) {
            val toAccount = transaction.toAccount
            transactionAccountDao.insert(TransactionAccountEntity(toAccount.blockchainAddress, toAccount.accountId, toAccount.ownerName))
            transactionDao.insert(TransactionEntity(transaction.id, transaction.toAccount.blockchainAddress, transaction.fromValue, transaction.fromCurrency,
                    transaction.toValue, transaction.toCurrency, transaction.fee, transaction.createdAt, transaction.updatedAt, transaction.status, transaction.fiatValue,
                    transaction.fiatCurrency))
            transaction.blockchainTxIds.forEach { blockchainIdDao.insert(BlockchainId(it, transaction.id)) }
            for (transactionAccount in transaction.fromAccount) {
                transactionAccountDao.insert(TransactionAccountEntity(transactionAccount.blockchainAddress, transactionAccount.accountId, transactionAccount.ownerName))
                transactionAccountJoinDao.insert(TransactionAccountJoin(transaction.id, transactionAccount.blockchainAddress))
            }
        }

        val qqq = transactionDao.loadTransactionsWithAddresses()
        transactions.forEach {
            if (it.blockchainTxIds.isNotEmpty())
                print("pppp")
        }
        print("success")
    }

    private fun updateAccounts() {
        val mapper = AccountMapper(CurrencyConverter(rates))
        if (accounts.isNotEmpty() && rates.isNotEmpty()) {
            cards = ArrayList()
            accounts.forEach { cards.add(mapper.map(it)) }
            updateAccounts.value = cards
        }
    }

    private fun updateData() {
        userRepository.refreshUser(::handleError)

        accountsRepository.refreshAccounts(::handleError)
    }

    fun onAccountClicked(position: Int, element: View, transition: String) {
        val bundle = Bundle()
        bundle.putInt("POSITION", position)
        getNavigator()?.showAccountFragment(bundle, element, transition)
    }

}