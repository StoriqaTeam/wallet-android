package com.storiqa.storiqawallet.ui.main.receive

import android.graphics.Bitmap
import androidx.databinding.ObservableField
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.storiqa.storiqawallet.common.NonNullMutableLiveData
import com.storiqa.storiqawallet.common.NonNullObservableField
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.data.preferences.IUserDataStorage
import com.storiqa.storiqawallet.data.repository.IAccountsRepository
import com.storiqa.storiqawallet.data.repository.IRatesRepository
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
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

    var accounts = NonNullMutableLiveData<List<Account>>(ArrayList())

    var currentPosition = 0

    val qrCode = ObservableField<Bitmap>()
    val address = NonNullObservableField("")

    init {
        setNavigator(navigator)

        accountsRepository
                .getAccounts(userData.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { accounts.value = it.reversed() }
    }

    fun onAccountSelected(position: Int) {
        currentPosition = position

        val blockchainAddress = accounts.value[position].accountAddress
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
        copyToClipboard.value = accounts.value[currentPosition].accountAddress
    }
}