package com.storiqa.storiqawallet.data.network.errors

import com.storiqa.storiqawallet.data.network.responses.ExchangeRateResponse

data class RefreshRateResponse(
        val rate: ExchangeRateResponse,
        val isNewRate: Boolean
)