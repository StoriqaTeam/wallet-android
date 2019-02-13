package com.storiqa.storiqawallet.data.network.requests

import com.storiqa.storiqawallet.data.model.Currency

data class FeeRequest(
        val currency: Currency,
        val accountAddress: String)
