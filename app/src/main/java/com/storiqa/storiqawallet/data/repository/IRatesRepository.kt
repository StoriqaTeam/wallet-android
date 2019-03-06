package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.db.entity.RateEntity
import com.storiqa.storiqawallet.data.model.Currency
import io.reactivex.Flowable
import io.reactivex.Single

interface IRatesRepository {

    fun getRates(): Flowable<List<RateEntity>>

    fun refreshRates(errorHandler: (Exception) -> Unit)

    fun updateRates(): Single<HashMap<Currency, HashMap<Currency, Double>>>

}