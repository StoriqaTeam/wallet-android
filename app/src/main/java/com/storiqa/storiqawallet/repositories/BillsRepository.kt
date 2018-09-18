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
            bills.add(Bill("info from id4","STQ", "10000", "5", "Peter Staranchuk"))
            bills.add(Bill("info from id5","STQ", "1000000", "6000000", "Peter Staranchuk"))
            bills.add(Bill("info from id6","STQ", "10000000", "7000000", "Peter Staranchuk"))
            bills.add(Bill("info from id1","STQ", "1000000000", "2000000", "Peter Staranchuk"))
            bills.add(Bill("info from id2","BTС", "2000", "3000", "Peter Staranchuk"))
            bills.add(Bill("info from id3","ETH", "3000000000", "4000000", "Peter Staranchuk"))
            emitter.onNext(bills.toTypedArray())
            emitter.onComplete()
        }.timeout(500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    }
}