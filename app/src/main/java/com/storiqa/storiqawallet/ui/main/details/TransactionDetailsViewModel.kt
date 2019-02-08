package com.storiqa.storiqawallet.ui.main.details

import androidx.databinding.ObservableField
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.NonNullObservableField
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.data.model.Transaction
import com.storiqa.storiqawallet.data.model.TransactionType
import com.storiqa.storiqawallet.data.repository.ITransactionsRepository
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import javax.inject.Inject

class TransactionDetailsViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val transactionsRepository: ITransactionsRepository) : BaseViewModel<IMainNavigator>() {

    val copyToClipboard = SingleLiveEvent<String>()

    var background = ObservableField<Int>()
    var icon = ObservableField<Int>()
    var amount = NonNullObservableField("")
    var fiat = NonNullObservableField("")
    var time = NonNullObservableField("")
    var direction = NonNullObservableField("")
    var blockchainAddress = NonNullObservableField("")
    var userName = NonNullObservableField("")
    var commission = NonNullObservableField("")
    var status = NonNullObservableField("")
    var title = NonNullObservableField("")
    private lateinit var fullAddress: String

    init {
        setNavigator(navigator)
    }

    fun loadTransaction(address: String, id: String) {
        transactionsRepository
                .getTransactionById(address, id)
                .subscribe { setData(it, address) }
    }

    private fun setData(transaction: Transaction, address: String) {
        amount.set(transaction.amount)
        fiat.set(transaction.fiat ?: "")
        time.set(transaction.time)
        status.set(transaction.status)
        commission.set(transaction.commission)
        title.set(transaction.type.getDescription())
        if (transaction.type == TransactionType.RECEIVE) {
            direction.set(App.res.getString(R.string.text_from))
            background.set(R.drawable.background_transaction_received)
        } else {
            direction.set(App.res.getString(R.string.text_to))
            background.set(R.drawable.background_transaction_sent)
        }
        icon.set(transaction.type.getTypeIcon())

        if (address == transaction.toAccount.blockchainAddress) {
            userName.set(transaction.fromAccount[0].ownerName ?: "")
            fullAddress = transaction.fromAccount[0].blockchainAddress
        } else {
            userName.set(transaction.toAccount.ownerName ?: "")
            fullAddress = transaction.toAccount.blockchainAddress
        }
        blockchainAddress.set("${fullAddress.substring(0, 8)}...." +
                fullAddress.substring(fullAddress.length - 4, fullAddress.length))
    }

    fun onCopyButtonClick() {
        copyToClipboard.value = fullAddress
    }

}