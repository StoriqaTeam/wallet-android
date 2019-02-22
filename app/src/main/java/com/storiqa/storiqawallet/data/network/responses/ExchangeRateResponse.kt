package com.storiqa.storiqawallet.data.network.responses

import java.math.BigDecimal

data class ExchangeRateResponse(
        val id: String,
        val from: String,
        val to: String,
        val amount: BigDecimal,
        val rate: Double,
        val expiration: String
)