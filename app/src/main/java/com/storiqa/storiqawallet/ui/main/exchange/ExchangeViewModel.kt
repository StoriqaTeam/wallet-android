package com.storiqa.storiqawallet.ui.main.exchange

import android.annotation.SuppressLint
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableLong
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.common.CurrencyConverter
import com.storiqa.storiqawallet.common.CurrencyFormatter
import com.storiqa.storiqawallet.common.NonNullObservableField
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.data.db.entity.AccountEntity
import com.storiqa.storiqawallet.data.db.entity.RateEntity
import com.storiqa.storiqawallet.data.mapper.AccountMapper
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.data.model.Currency
import com.storiqa.storiqawallet.data.network.WalletApi
import com.storiqa.storiqawallet.data.network.errors.ErrorPresenterFields
import com.storiqa.storiqawallet.data.network.requests.ExchangeRateRequest
import com.storiqa.storiqawallet.data.network.requests.RefreshRateRequest
import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import com.storiqa.storiqawallet.data.preferences.IUserDataStorage
import com.storiqa.storiqawallet.data.repository.IAccountsRepository
import com.storiqa.storiqawallet.data.repository.IRatesRepository
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import com.storiqa.storiqawallet.utils.SignUtil
import com.storiqa.storiqawallet.utils.getTimestampLong
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.lang.System.currentTimeMillis
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import javax.inject.Inject

class ExchangeViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val accountsRepository: IAccountsRepository,
            private val ratesRepository: IRatesRepository,
            private val walletApi: WalletApi,
            private val appData: IAppDataStorage,
            private val userData: IUserDataStorage,
            private val signUtil: SignUtil) : BaseViewModel<IMainNavigator>() {

    val updateAccounts = SingleLiveEvent<List<Account>>()

    var accounts: ArrayList<Account> = ArrayList()
    var toPosition = 0
    var fromPosition = 0

    val exchangeButtonEnabled = ObservableBoolean(false)
    val amountRemittance = NonNullObservableField("")
    val amountCollection = NonNullObservableField("")
    val errorAmountRemittance = NonNullObservableField("")
    val errorAmountCollection = NonNullObservableField("")
    val exchangeRate = NonNullObservableField("")
    val countDown = ObservableLong(-1)

    private var decimalRemittance = BigDecimal(0)
    private var decimalCollection = BigDecimal(0)
    private var isAmountRemittanceLastEdited = true
    private var obtainingRateRequest: Disposable? = null
    private val currencyFormatter = CurrencyFormatter()
    private var rateId = ""

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

    fun onAccountFromSelected(position: Int) {
        fromPosition = position
        onAccountChanged()

    }

    fun onAccountToSelected(position: Int) {
        toPosition = position
        onAccountChanged()
    }

    fun onAmountRemittanceInputted() {
        isAmountRemittanceLastEdited = true
        errorAmountRemittance.set("")
        amountCollection.set("")
        onAmountChanged(amountRemittance.get(), accounts[fromPosition].currency)
    }

    fun onAmountCollectionInputted() {
        isAmountRemittanceLastEdited = false
        errorAmountCollection.set("")
        amountRemittance.set("")
        onAmountChanged(amountCollection.get(), accounts[toPosition].currency)
    }

    fun onCountdownFinish() {
        countDown.set(0)
        requestRefreshRate()
    }

    fun onExchangeButtonClicked() {
    }

    private fun onAccountChanged() {
        hideKeyboard()
        if (isAmountRemittanceLastEdited)
            amountCollection.set("")
        else
            amountRemittance.set("")
        if (fromPosition == toPosition) {
            countDown.set(-1)
            return
        }

        countDown.set(0)
        if (isAmountRemittanceLastEdited)
            onAmountChanged(amountRemittance.get(), accounts[fromPosition].currency)
        else
            onAmountChanged(amountCollection.get(), accounts[toPosition].currency)
    }

    private fun onAmountChanged(amount: String, currency: Currency) {
        val decimalAmount = convertStringToDecimal(amount)
        obtainingRateRequest?.dispose()
        if (decimalAmount.compareTo(BigDecimal.ZERO) != 0 && toPosition != fromPosition) {
            //todo cache check
            requestExchangeRate(amount, currency)
            countDown.set(0)
        } else {
            countDown.set(-1)
            exchangeButtonEnabled.set(false)
        }
    }

    @SuppressLint("CheckResult")
    private fun requestExchangeRate(amount: String, amountCurrency: Currency) {
        rateId = UUID.randomUUID().toString()
        val amountInMinUnits = BigDecimal(amount)
                .setScale(amountCurrency.getSignificantDigits(), RoundingMode.DOWN)
                .movePointRight(amountCurrency.getSignificantDigits())
        val email = appData.currentUserEmail
        val token = appData.token
        val signHeader = signUtil.createSignHeader(email)
        val request = ExchangeRateRequest(
                rateId,
                accounts[fromPosition].currency.currencyISO.toLowerCase(),
                accounts[toPosition].currency.currencyISO.toLowerCase(),
                amountCurrency.currencyISO.toLowerCase(),
                amountInMinUnits
        )
        obtainingRateRequest = walletApi
                .getExchangeRate(signHeader.timestamp, signHeader.deviceId, signHeader.signature, "Bearer $token", request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateRates(it.rate, getTimestampLong(it.expiration))
                }, {
                    handleError(it as Exception)
                })
    }

    private fun requestRefreshRate() {
        val email = appData.currentUserEmail
        val token = appData.token
        val signHeader = signUtil.createSignHeader(email)
        val request = RefreshRateRequest(rateId)
        obtainingRateRequest = walletApi
                .refreshRate(signHeader.timestamp, signHeader.deviceId, signHeader.signature, "Bearer $token", request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateRates(it.rate.rate, getTimestampLong(it.rate.expiration))
                }, {
                    handleError(it as Exception)
                })
    }

    override fun showErrorFields(errorPresenter: ErrorPresenterFields) {
        errorPresenter.fieldErrors.forEach {
            it.forEach { (key, value) ->
                when (key) {
                    "value" -> {
                        countDown.set(-1)
                        if (isAmountRemittanceLastEdited)
                            errorAmountRemittance.set(App.res.getString(value))
                        else
                            errorAmountCollection.set(App.res.getString(value))
                    }
                }
            }
        }
    }

    private fun mapAccounts(rates: List<RateEntity>, accounts: List<AccountEntity>): List<Account> {
        val mapper = AccountMapper(CurrencyConverter(rates))
        if (accounts.isNotEmpty() && rates.isNotEmpty()) {
            val tempAccounts = ArrayList<Account>()
            accounts.reversed().forEach { tempAccounts.add(mapper.map(it)) }
            this.accounts = tempAccounts
        }
        return this.accounts
    }

    private fun convertStringToDecimal(amount: String): BigDecimal {
        if (amount.isEmpty() || amount == ".")
            return BigDecimal.ZERO

        return BigDecimal(amount)
    }

    private fun updateRates(rate: Double, expiration: Long) {
        countDown.set(expiration - currentTimeMillis() - 270000)
        exchangeRate.set(buildString {
            append("1 ")
            append(accounts[fromPosition].currency.getSymbol())
            append(" = ")
            append(currencyFormatter.getAmountFromDouble(rate, accounts[toPosition].currency))
            append(" ")
            append(accounts[toPosition].currency.getSymbol())
        })

        if (isAmountRemittanceLastEdited) {
            val calculatedAmount = currencyFormatter
                    .getAmountFromDouble(amountRemittance.get().toDouble() * rate,
                            accounts[toPosition].currency)
            amountCollection.set(calculatedAmount)
        } else {
            val calculatedAmount = currencyFormatter
                    .getAmountFromDouble(amountCollection.get().toDouble() / rate,
                            accounts[fromPosition].currency)
            amountRemittance.set(calculatedAmount)
        }
    }

    private fun checkExchangeButtonAvailable() {
        if (amountRemittance.get().isNotEmpty() && amountCollection.get().isNotEmpty()
                && decimalRemittance.compareTo(BigDecimal.ZERO) != 0
                && decimalCollection.compareTo(BigDecimal.ZERO) != 0) {
            exchangeButtonEnabled.set(true)
            return
        }
        exchangeButtonEnabled.set(false)
    }
}