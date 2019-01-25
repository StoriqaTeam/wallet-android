package com.storiqa.storiqawallet.ui.main.account

import com.storiqa.storiqawallet.common.CurrencyConverter
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.data.IUserDataStorage
import com.storiqa.storiqawallet.data.db.entity.AccountEntity
import com.storiqa.storiqawallet.data.db.entity.RateEntity
import com.storiqa.storiqawallet.data.mapper.AccountMapper
import com.storiqa.storiqawallet.data.model.Card
import com.storiqa.storiqawallet.data.repository.IAccountsRepository
import com.storiqa.storiqawallet.data.repository.IRatesRepository
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class AccountViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val accountsRepository: IAccountsRepository,
            private val ratesRepository: IRatesRepository,
            private val userData: IUserDataStorage) : BaseViewModel<IMainNavigator>() {

    val updateAccounts = SingleLiveEvent<ArrayList<Card>>()

    var cards: ArrayList<Card> = ArrayList()

    private var accounts: List<AccountEntity> = ArrayList()
    private var rates: List<RateEntity> = ArrayList()

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

    private fun updateAccounts() {
        val mapper = AccountMapper(CurrencyConverter(rates))
        if (accounts.isNotEmpty() && rates.isNotEmpty()) {
            cards = ArrayList()
            accounts.forEach { cards.add(mapper.map(it)) }
            updateAccounts.value = cards
        }
    }
}