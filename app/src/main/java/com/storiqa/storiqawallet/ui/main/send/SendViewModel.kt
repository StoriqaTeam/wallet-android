package com.storiqa.storiqawallet.ui.main.send

import androidx.databinding.ObservableBoolean
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.CurrencyConverter
import com.storiqa.storiqawallet.common.NonNullObservableField
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.data.db.entity.AccountEntity
import com.storiqa.storiqawallet.data.db.entity.RateEntity
import com.storiqa.storiqawallet.data.mapper.AccountMapper
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.data.preferences.IUserDataStorage
import com.storiqa.storiqawallet.data.repository.IAccountsRepository
import com.storiqa.storiqawallet.data.repository.IRatesRepository
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import com.storiqa.storiqawallet.utils.isAddressValid
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SendViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val accountsRepository: IAccountsRepository,
            private val ratesRepository: IRatesRepository,
            private val userData: IUserDataStorage
) : BaseViewModel<IMainNavigator>() {

    val updateAccounts = SingleLiveEvent<List<Account>>()
    val scanQrCode = SingleLiveEvent<Boolean>()
    val showInvalidAddressError = SingleLiveEvent<Boolean>()

    var accounts: ArrayList<Account> = ArrayList()

    var currentPosition = 0

    val sendButtonEnabled = ObservableBoolean(false)
    val address = NonNullObservableField("")
    val amount = NonNullObservableField("")
    val amountFiat = NonNullObservableField("")
    val addressError = NonNullObservableField("")

    init {
        setNavigator(navigator)

        address.addOnPropertyChanged {
            addressError.set("")
            checkSendButtonAvailable()
        }
        amount.addOnPropertyChanged { checkSendButtonAvailable() }
        amountFiat.addOnPropertyChanged { checkSendButtonAvailable() }

        Flowable.combineLatest(ratesRepository.getRates(),
                accountsRepository.getAccounts(userData.id),
                BiFunction(::mapAccounts))
                .distinct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { updateAccounts.value = it }
    }

    fun onAccountSelected(position: Int) {
        currentPosition = position
    }

    fun onSendButtonClicked() {

    }

    fun onScanButtonClick() {
        scanQrCode.trigger()
    }

    fun onQrCodeScanned(blockchainAddress: String) {
        if (isAddressValid(blockchainAddress, accounts[currentPosition].currency))
            address.set(blockchainAddress)
        else
            showInvalidAddressError.trigger()
    }

    private fun checkSendButtonAvailable() {
        if (address.get().isNotEmpty() && amount.get().isNotEmpty() && amountFiat.get().isNotEmpty())
            sendButtonEnabled.set(true)
        else
            sendButtonEnabled.set(false)
    }

    private fun mapAccounts(rates: List<RateEntity>, accounts: List<AccountEntity>): List<Account> {
        val mapper = AccountMapper(CurrencyConverter(rates))
        if (accounts.isNotEmpty() && rates.isNotEmpty()) {
            this.accounts = ArrayList()
            accounts.reversed().forEach { this.accounts.add(mapper.map(it)) }
        }
        return this.accounts
    }

    fun validateAddress() {
        if (address.get().isNotEmpty() && !isAddressValid(address.get(), accounts[currentPosition].currency))
            addressError.set(App.res.getString(R.string.error_address_not_valid))
    }
}