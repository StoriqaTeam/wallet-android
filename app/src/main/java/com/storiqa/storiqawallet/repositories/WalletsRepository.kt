package com.storiqa.storiqawallet.repositories

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class WalletsRepository {

    fun getWallets(phoneNumbers : Array<String>) : Observable<Map<String, String>> {
        return Observable.create<Map<String, String>> {emitter ->
            val result = HashMap<String, String>()

            for(i in 0 until phoneNumbers.size) {
                if(i%2==0) {
                    result.put(phoneNumbers[i], "wxcdf123dnksaldsa21313")
                }
            }

            emitter.onNext(result)
            emitter.onComplete()
        }.timeout(1, TimeUnit.SECONDS).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    }
}