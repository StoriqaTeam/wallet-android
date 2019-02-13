package com.storiqa.storiqawallet.data.network.responses

import com.storiqa.storiqawallet.data.model.Currency

data class FeeResponse(
        val currency: Currency,
        val fees: List<Fee>)

data class Fee(
        val value: String,
        val estimatedTime: String)