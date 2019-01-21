package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.db.entity.Rate
import io.reactivex.Flowable
import io.reactivex.Observable

interface IRatesRepository {

    fun getRates(): Flowable<List<Rate>>

    fun refreshRates(errorHandler: (Exception) -> Unit)

    fun updateRates(): Observable<HashMap<String, HashMap<String, Double>>>

}