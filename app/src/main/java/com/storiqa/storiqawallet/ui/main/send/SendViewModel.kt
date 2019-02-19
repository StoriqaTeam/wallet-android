package com.storiqa.storiqawallet.ui.main.send

import android.annotation.SuppressLint
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
import com.storiqa.storiqawallet.data.network.errors.ErrorPresenterFields
import com.storiqa.storiqawallet.data.network.requests.CreateTransactionRequest
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
import java.math.BigDecimal
import java.util.*
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
    val showSuccessMessage = SingleLiveEvent<Boolean>()

    var currentPosition = 0

    val sendButtonEnabled = ObservableBoolean(false)
    val address = NonNullObservableField("")
    val amountCrypto = NonNullObservableField("")
    val amountFiat = NonNullObservableField("")
    val addressError = NonNullObservableField("")

    val seekBarFeePosition = ObservableInt(0)
    val feesSize = ObservableInt(0)
    val feeAmount = NonNullObservableField("")
    val estimatedTime = NonNullObservableField("")
    val isNotEnough = ObservableBoolean(false)

    private val currencyFormatter = CurrencyFormatter()
    private var currencyConverter: ICurrencyConverter? = null

    var accounts: ArrayList<Account> = ArrayList()
    private var fees: List<Fee> = ArrayList()
    private lateinit var feeCurrency: Currency
    private var isAmountCryptoLastEdited = true
    private var total = ""

    init {
        setNavigator(navigator)

        address.addOnPropertyChanged {
            validateAddress()
            checkSendButtonAvailable()
        }
        amountCrypto.addOnPropertyChanged { checkSendButtonAvailable() }
        amountFiat.addOnPropertyChanged { checkSendButtonAvailable() }
        seekBarFeePosition.addOnPropertyChanged { checkSendButtonAvailable() }
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

    override fun showErrorFields(errorPresenter: ErrorPresenterFields) {
        errorPresenter.fieldErrors.forEach {
            it.forEach { (key, value) ->
                when (key) {
                    "account" -> {
                        addressError.set(App.res.getString(value))
                        sendButtonEnabled.set(false)
                    }
                }
            }
        }
    }

    fun onAccountSelected(position: Int) {
        hideKeyboard()
        currentPosition = position
        sendButtonEnabled.set(false)
        isNotEnough.set(false)

        fees = ArrayList()
        feesSize.set(0)
        validateAddress()
        if (isAmountCryptoLastEdited)
            calculateAmountFiat()
        else
            calculateAmountCrypto()
    }

    fun onSendButtonClicked() {
        hideKeyboard()
        getNavigator()?.showSendConfirmationDialog(
                address.get(),
                amountCrypto.get(),
                feeAmount.get(),
                total,
                ::sendTransactionRequest
        )
    }

    fun onScanButtonClick() {
        scanQrCode.trigger()
    }

    fun onQrCodeScanned(blockchainAddress: String) {
        accounts.forEach {
            if (isAddressValid(blockchainAddress, it.currency)) {
                address.set(blockchainAddress)
                return
            }
        }
        showInvalidAddressError.trigger()
    }

    fun validateAddress() {
        if (address.get().isEmpty())
            return
        if (isAddressValid(address.get(), accounts[currentPosition].currency)) {
            addressError.set("")
            Thread {
                requestFees(address.get())
            }.start()
        } else {
            addressError.set(App.res.getString(R.string.error_address_not_valid))
            fees = ArrayList()
            feesSize.set(0)
        }
    }

    fun calculateAmountCrypto() {
        isAmountCryptoLastEdited = false
        if (currencyConverter == null || amountFiat.get().isEmpty() || amountFiat.get() == ".")
            amountCrypto.set("")
        else
            amountCrypto.set(currencyConverter?.convertBalance(amountFiat.get(), Currency.USD, accounts[currentPosition].currency)
                    ?: "")
    }

    fun calculateAmountFiat() {
        isAmountCryptoLastEdited = true
        if (currencyConverter == null || amountCrypto.get().isEmpty() || amountCrypto.get() == ".")
            amountFiat.set("")
        else
            amountFiat.set(currencyConverter?.convertBalance(amountCrypto.get(), accounts[currentPosition].currency, Currency.USD)
                    ?: "")
    }

    @SuppressLint("CheckResult")
    private fun sendTransactionRequest() {
        showLoadingDialog()
        val email = appData.currentUserEmail
        val token = appData.token
        val signHeader = signUtil.createSignHeader(email)
        val currency = accounts[currentPosition].currency
        val request = CreateTransactionRequest(
                UUID.randomUUID().toString(),
                userData.id,
                accounts[currentPosition].id,
                address.get().removePrefix("0x"),
                "address",
                currency.currencyISO.toLowerCase(),
                currencyFormatter.getStringAmount(amountCrypto.get(), currency),
                currency.currencyISO.toLowerCase(),
                amountFiat.get(),
                Currency.USD.currencyISO.toLowerCase(),
                currencyFormatter.getStringAmount(fees[seekBarFeePosition.get()].value, currency)

        )
        walletApi
                .createTransaction(signHeader.timestamp, signHeader.deviceId, signHeader.signature, "Bearer $token", request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    hideLoadingDialog()
                    showSuccessMessage.trigger()
                }, {
                    hideLoadingDialog()
                    handleError(it as Exception)
                })
    }

    @SuppressLint("CheckResult")
    private fun requestFees(address: String) {
        val email = appData.currentUserEmail
        val token = appData.token
        val signHeader = signUtil.createSignHeader(email)
        val request = FeeRequest(accounts[currentPosition].currency.currencyISO.toLowerCase(), address.removePrefix("0x"))
        walletApi
                .calculateFee(signHeader.timestamp, signHeader.deviceId, signHeader.signature, "Bearer $token", request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    fees = it.fees
                    if (it.fees.size == 1 && it.fees[0].estimatedTime == 0) {
                        checkSendButtonAvailable()
                        return@subscribe
                    }

                    feeCurrency = it.currency
                    feesSize.set(fees.size)
                    seekBarFeePosition.set(0)
                    feeAmount.set(currencyFormatter.getFormattedAmount(fees[0].value, feeCurrency))
                    estimatedTime.set(getPresentableTime(fees[0].estimatedTime))
                    checkSendButtonAvailable()
                }, {
                    handleError(it as Exception)
                })
    }

    private fun checkSendButtonAvailable() {
        if (address.get().isNotEmpty() && addressError.get().isEmpty() && fees.isNotEmpty()
                && amountCrypto.get().isNotEmpty() && amountFiat.get().isNotEmpty()
                && amountCrypto.get() != "." && amountFiat.get() != "."
                && BigDecimal(amountCrypto.get()).compareTo(BigDecimal.ZERO) != 0) {
            if (isEnoughMoneyForSend()) {
                sendButtonEnabled.set(true)
                isNotEnough.set(false)
                return
            } else
                isNotEnough.set(true)
        }
        sendButtonEnabled.set(false)
    }

    private fun mapAccounts(rates: List<RateEntity>, accounts: List<AccountEntity>): List<Account> {
        currencyConverter = CurrencyConverter(rates)
        val mapper = AccountMapper(CurrencyConverter(rates))
        if (accounts.isNotEmpty() && rates.isNotEmpty()) {
            this.accounts = ArrayList()
            accounts.reversed().forEach { this.accounts.add(mapper.map(it)) }
        }
        return this.accounts
    }

    private fun isEnoughMoneyForSend(): Boolean {
        val feeDecimal = if (feesSize.get() == 0)
            BigDecimal.ZERO
        else
            BigDecimal(fees[seekBarFeePosition.get()].value).movePointLeft(feeCurrency.getSignificantDigits())
        val amountDecimal = BigDecimal(amountCrypto.get())
        val balanceDecimal = BigDecimal(accounts[currentPosition].balance)
                .movePointLeft(accounts[currentPosition].currency.getSignificantDigits())
        total = "${feeDecimal + amountDecimal} ${accounts[currentPosition].currency.getSymbol()}"
        return feeDecimal + amountDecimal <= balanceDecimal
    }
}