package com.storiqa.storiqawallet.repositories

import com.storiqa.storiqawallet.objects.Bill
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class BillsRepository {

    fun getBills() : Observable<Array<Bill>> {
        return Observable.create<Array<Bill>> { emitter ->
            val bills = arrayListOf<Bill>()
            bills.add(Bill("info from id4","STQ", "10,000.00", "5.00", "Peter Staranchuk"))
            bills.add(Bill("info from id5","STQ", "1,000,000.00", "6,000,000.00", "Peter Staranchuk"))
            bills.add(Bill("info from id6","STQ", "10,000,000.00", "7,000,000.00", "Peter Staranchuk"))
            bills.add(Bill("info from id1","STQ", "1000,000,000.00", "2,000,000.00", "Peter Staranchuk"))
            bills.add(Bill("info from id2","BTC", "2,000.00", "3,000.00", "Peter Staranchuk"))
            bills.add(Bill("info from id3","ETH", "3,000,000,000.00", "4,000,000.00", "Peter Staranchuk"))
            emitter.onNext(bills.toTypedArray())
            emitter.onComplete()
        }.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    }
}