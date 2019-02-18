package com.storiqa.storiqawallet.ui.dialogs

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
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

    fun initData(address: String, amount: String, fee: String, total: String) {
        this.address = ObservableField(address)
        this.amount = ObservableField(amount)
        this.fee = ObservableField(fee)
        this.total = ObservableField(total)
    }

    fun onConfirmButtonClicked() {
        confirmButtonClicked.trigger()
    }

    fun onCancelButtonClicked() {
        cancelButtonClicked.trigger()
    }

}