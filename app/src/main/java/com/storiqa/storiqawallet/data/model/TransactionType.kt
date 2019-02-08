package com.storiqa.storiqawallet.data.model

import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R

enum class TransactionType {
    SEND, RECEIVE;

    fun getTypeIcon(): Int = when (this) {
        SEND -> R.drawable.ic_send
        RECEIVE -> R.drawable.ic_receive
    }

    fun getDescription(): String = when (this) {
        SEND -> App.res.getString(R.string.text_transaction_sent)
        RECEIVE -> App.res.getString(R.string.text_transaction_received)
    }
}