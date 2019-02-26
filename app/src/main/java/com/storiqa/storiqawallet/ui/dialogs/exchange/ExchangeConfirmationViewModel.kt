package com.storiqa.storiqawallet.ui.dialogs.exchange

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.storiqa.storiqawallet.common.SingleLiveEvent
import javax.inject.Inject

class ExchangeConfirmationViewModel
@Inject
constructor() : ViewModel() {

    lateinit var remittanceAccount: ObservableField<String>
    lateinit var collectionAccount: ObservableField<String>
    lateinit var remittanceAmount: ObservableField<String>
    lateinit var collectionAmount: ObservableField<String>

    val confirmButtonClicked = SingleLiveEvent<Void>()
    val cancelButtonClicked = SingleLiveEvent<Void>()

    fun initData(remittanceAccount: String, remittanceAmount: String, collectionAccount: String, collectionAmount: String) {
        this.remittanceAccount = ObservableField(remittanceAccount)
        this.collectionAccount = ObservableField(collectionAccount)
        this.remittanceAmount = ObservableField(remittanceAmount)
        this.collectionAmount = ObservableField(collectionAmount)
    }

    fun onConfirmButtonClicked() {
        confirmButtonClicked.trigger()
    }

    fun onCancelButtonClicked() {
        cancelButtonClicked.trigger()
    }

}