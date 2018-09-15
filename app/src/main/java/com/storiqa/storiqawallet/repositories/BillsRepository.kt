package com.storiqa.storiqawallet.repositories

import com.storiqa.storiqawallet.objects.Bill
import io.reactivex.Observable

class BillsRepository {

    fun getBills() : Observable<Array<Bill>> {
        return Observable.create<Array<Bill>> {
            emitter ->

            val bills = arrayListOf<Bill>()
            bills.add(Bill("info from id1","STQ", "1000000000", "2000000", "Peter Staranchuk", true))
            bills.add(Bill("info from id2","BTQ", "2000", "3000", "Peter Staranchuk", false))
            bills.add(Bill("info from id3","ETH", "3000000000", "4000000", "Peter Staranchuk", true))
            bills.add(Bill("info from id4","STQ", "40", "5", "Peter Staranchuk", false))
            bills.add(Bill("info from id5","BTQ", "5000000000", "6000000", "Peter Staranchuk", true))
            bills.add(Bill("info from id6","ETH", "6000000000", "7000000", "Peter Staranchuk", true))
            emitter.onNext(bills.toTypedArray())
            emitter.onComplete()
        }
    }
}