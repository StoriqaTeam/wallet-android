package com.storiqa.storiqawallet.repositories

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class CurrencyConverterRepository {

    fun getLastConverCources(): Observable<HashMap<String, Double>> {
        return Observable.create<HashMap<String, Double>> {emitter ->
            val map = HashMap<String, Double>()
            map["STQ"] = 1.0
            map["BTC"] = 0.5
            map["ETH"] = 2.0

            emitter.onNext(map)
            emitter.onComplete()
        }.timeout(500, TimeUnit.MILLISECONDS)
    }
}