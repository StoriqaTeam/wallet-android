package com.storiqa.storiqawallet.ui.main.receive

import android.graphics.Bitmap
import androidx.databinding.ObservableField
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.storiqa.storiqawallet.common.CurrencyConverter
import com.storiqa.storiqawallet.common.NonNullObservableField
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.data.db.entity.AccountEntity
import com.storiqa.storiqawallet.data.db.entity.RateEntity
import com.storiqa.storiqawallet.data.mapper.AccountMapper
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.data.preferences.IUserDataStorage
import com.storiqa.storiqawallet.data.repository.IAccountsRepository
import com.storiqa.storiqawallet.data.repository.IRatesRepository
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReceiveViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val accountsRepository: IAccountsRepository,
            private val ratesRepository: IRatesRepository,
            private val userData: IUserDataStorage
) : BaseViewModel<IMainNavigator>() {


    val updateAccounts = SingleLiveEvent<List<Account>>()
    val shareQrCode = SingleLiveEvent<Bitmap>()
    val copyToClipboard = SingleLiveEvent<String>()

    var accounts: ArrayList<Account> = ArrayList()

    var currentPosition = 0

    val qrCode = ObservableField<Bitmap>()
    val address = NonNullObservableField("")

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

    private fun mapAccounts(rates: List<RateEntity>, accounts: List<AccountEntity>): List<Account> {
        val mapper = AccountMapper(CurrencyConverter(rates))
        if (accounts.isNotEmpty() && rates.isNotEmpty()) {
            this.accounts = ArrayList()
            accounts.reversed().forEach { this.accounts.add(mapper.map(it)) }
        }
        return this.accounts
    }

    fun onAccountSelected(position: Int) {
        currentPosition = position

        val blockchainAddress = accounts[position].accountAddress
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap(blockchainAddress, BarcodeFormat.QR_CODE, 400, 400)
        qrCode.set(bitmap)

        address.set(blockchainAddress)
    }

    fun onQrCodeClicked(): Boolean {
        shareQrCode.value = qrCode.get()
        return true
    }

    fun onCopyButtonClick() {
        copyToClipboard.value = accounts[currentPosition].accountAddress
    }

}