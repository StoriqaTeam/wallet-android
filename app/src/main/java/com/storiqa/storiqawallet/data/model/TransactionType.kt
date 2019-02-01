package com.storiqa.storiqawallet.data.model

import com.storiqa.storiqawallet.R

enum class TransactionType {
    SEND, RECEIVE;

    fun getTypeIcon(): Int = when (this) {
        SEND -> R.drawable.send_icon_round
        RECEIVE -> R.drawable.deposit_icon_round
    }
}