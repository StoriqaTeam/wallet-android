package com.storiqa.storiqawallet.network

import com.storiqa.storiqawallet.data.model.Currency
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoCompareApi {

    @GET("data/pricemulti")
    fun getRates(
            @Query("fsyms") fsyms: String,
            @Query("tsyms") tsyms: String): Observable<HashMap<Currency, HashMap<Currency, Double>>>
}