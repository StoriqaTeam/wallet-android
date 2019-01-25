package com.storiqa.storiqawallet.ui.main.wallet

import com.storiqa.storiqawallet.common.CurrencyConverter
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.data.IAppDataStorage
import com.storiqa.storiqawallet.data.ITokenProvider
import com.storiqa.storiqawallet.data.IUserDataStorage
import com.storiqa.storiqawallet.data.db.entity.AccountEntity
import com.storiqa.storiqawallet.data.db.entity.RateEntity
import com.storiqa.storiqawallet.data.mapper.AccountMapper
import com.storiqa.storiqawallet.data.model.Card
import com.storiqa.storiqawallet.data.polling.ShortPolling
import com.storiqa.storiqawallet.data.repository.IAccountsRepository
import com.storiqa.storiqawallet.data.repository.IRatesRepository
import com.storiqa.storiqawallet.data.repository.IUserRepository
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class WalletViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val userRepository: IUserRepository,
            private val accountsRepository: IAccountsRepository,
            private val ratesRepository: IRatesRepository,
            private val userData: IUserDataStorage,
            private val appData: IAppDataStorage,
            private val tokenProvider: ITokenProvider) : BaseViewModel<IMainNavigator>() {

    val updateAccounts = SingleLiveEvent<ArrayList<Card>>()

    var cards: ArrayList<Card> = ArrayList()
    var accounts: List<AccountEntity> = ArrayList()
    var rates: List<RateEntity> = ArrayList()

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

        //accounts.subscribe()

        //ratesRepository.refreshRates(::handleError)
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

        //get UserEntity from BD

        /*val user = userRepository.getUser(userData.email)

        user.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("TAGGG", "onNext ${it.id}")
                    print(it)
                }, {
                    Log.d("TAGGG", "onError ${it.message}")
                    print(it)
                })*/
    }

}