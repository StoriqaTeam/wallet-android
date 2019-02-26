package com.storiqa.storiqawallet.data.model

import java.math.BigDecimal

data class ExchangeRateKey(
        val from: String,
        val to: String,
        val amount: BigDecimal
)