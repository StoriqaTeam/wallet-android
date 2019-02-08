package com.storiqa.storiqawallet.data.model

import com.storiqa.storiqawallet.R

enum class TransactionType {
    SEND, RECEIVE;

    fun getTypeIcon(): Int = when (this) {
        SEND -> R.drawable.ic_send
        RECEIVE -> R.drawable.ic_receive
    }
}