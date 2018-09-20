package com.storiqa.storiqawallet.repositories

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CurrencyConverterRepository {

    fun getLastConverCources(): Observable<HashMap<String, Double>> {
        return Observable.create<HashMap<String, Double>> { emitter ->
            val map = HashMap<String, Double>()
            map["STQ"] = 1.0
            map["BTC"] = 0.5
            map["ETH"] = 2.0

            emitter.onNext(map)
            emitter.onComplete()
        }.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .timeout(2000, TimeUnit.MILLISECONDS)
    }
}