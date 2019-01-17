package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.db.entity.Rate
import io.reactivex.Flowable

interface IRatesRepository {

    fun getRates(): Flowable<List<Rate>>

    fun refreshRates(errorHandler: (Exception) -> Unit)

}