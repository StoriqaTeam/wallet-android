package com.storiqa.storiqawallet.ui.main.send

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.*
import com.storiqa.storiqawallet.data.db.entity.AccountEntity
import com.storiqa.storiqawallet.data.db.entity.RateEntity
import com.storiqa.storiqawallet.data.mapper.AccountMapper
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.data.model.Currency
import com.storiqa.storiqawallet.data.network.WalletApi
import com.storiqa.storiqawallet.data.network.requests.FeeRequest
import com.storiqa.storiqawallet.data.network.responses.Fee
import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import com.storiqa.storiqawallet.data.preferences.IUserDataStorage
import com.storiqa.storiqawallet.data.repository.IAccountsRepository
import com.storiqa.storiqawallet.data.repository.IRatesRepository
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import com.storiqa.storiqawallet.utils.SignUtil
import com.storiqa.storiqawallet.utils.getPresentableTime
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
            private val userData: IUserDataStorage,
            private val walletApi: WalletApi,
            private val appData: IAppDataStorage,
            private val signUtil: SignUtil
) : BaseViewModel<IMainNavigator>() {

    val updateAccounts = SingleLiveEvent<List<Account>>()
    val scanQrCode = SingleLiveEvent<Boolean>()
    val showInvalidAddressError = SingleLiveEvent<Boolean>()

    var accounts: ArrayList<Account> = ArrayList()
    private var fees: List<Fee> = ArrayList()
    private lateinit var feeCurrency: Currency

    var currentPosition = 0

    val sendButtonEnabled = ObservableBoolean(false)
    val address = NonNullObservableField("")
    val amount = NonNullObservableField("")
    val amountFiat = NonNullObservableField("")
    val addressError = NonNullObservableField("")

    val seekBarFeePosition = ObservableInt(0)
    val feesSize = ObservableInt(0)
    val feeAmount = NonNullObservableField("")
    val estimatedTime = NonNullObservableField("")

    private val currencyFormatter = CurrencyFormatter()

    init {
        setNavigator(navigator)

        address.addOnPropertyChanged {
            addressError.set("")
            checkSendButtonAvailable()
        }
        amount.addOnPropertyChanged { checkSendButtonAvailable() }
        amountFiat.addOnPropertyChanged { checkSendButtonAvailable() }
        seekBarFeePosition.addOnPropertyChanged {
            if (fees.isNotEmpty()) {
                feeAmount.set(currencyFormatter.getFormattedAmount(fees[it.get()].value, feeCurrency))
                estimatedTime.set(getPresentableTime(fees[it.get()].estimatedTime))
            }
        }

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

        fees = ArrayList()
        feesSize.set(0)
        validateAddress()
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

    fun validateAddress() {
        if (address.get().isEmpty())
            return
        if (isAddressValid(address.get(), accounts[currentPosition].currency)) {
            addressError.set("")
            requestFees(address.get())
        } else {
            addressError.set(App.res.getString(R.string.error_address_not_valid))
            fees = ArrayList()
            feesSize.set(0)
        }
    }

    private fun requestFees(address: String) {
        val email = appData.currentUserEmail
        val token = appData.token
        val signHeader = signUtil.createSignHeader(email)
        val request = FeeRequest(accounts[currentPosition].currency.currencyISO.toLowerCase(), address)
        walletApi
                .calculateFee(signHeader.timestamp, signHeader.deviceId, signHeader.signature, "Bearer $token", request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    feeCurrency = it.currency
                    fees = it.fees
                    feesSize.set(fees.size)
                    seekBarFeePosition.set(0)
                    feeAmount.set(currencyFormatter.getFormattedAmount(fees[0].value, feeCurrency))
                    estimatedTime.set(getPresentableTime(fees[0].estimatedTime))
                }, {
                    print(it)
                })
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
}