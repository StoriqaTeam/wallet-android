package com.storiqa.storiqawallet.ui.dialogs

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.SingleLiveEvent
import javax.inject.Inject

class SendConfirmationViewModel
@Inject
constructor() : ViewModel() {

    lateinit var address: ObservableField<String>
    lateinit var amount: ObservableField<String>
    lateinit var fee: ObservableField<String>
    lateinit var total: ObservableField<String>

    val confirmButtonClicked = SingleLiveEvent<Void>()
    val cancelButtonClicked = SingleLiveEvent<Void>()

    private lateinit var fullAddress: String

    fun initData(address: String, amount: String, fee: String, total: String) {
        fullAddress = address
        this.amount = ObservableField(if (amount.startsWith(".")) "0$amount" else amount)
        this.fee = ObservableField(if (fee.isEmpty()) App.res.getString(R.string.text_no_fee) else fee)
        this.total = ObservableField(total)
        this.address = ObservableField("${address.substring(0, 8)} . . . . " +
                address.substring(address.length - 4, address.length))
    }

    fun onConfirmButtonClicked() {
        confirmButtonClicked.trigger()
    }

    fun onCancelButtonClicked() {
        cancelButtonClicked.trigger()
    }

}