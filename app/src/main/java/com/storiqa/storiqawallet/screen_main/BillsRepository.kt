package com.storiqa.storiqawallet.screen_main

import com.storiqa.storiqawallet.objects.Bill
import io.reactivex.Observable

class BillsRepository {

    fun getBills() : Observable<List<Bill>> {
        return Observable.create<List<Bill>> {
            emitter ->

            val bills = arrayListOf<Bill>()
            bills.add(Bill("STQ", "1000000000", "2000000", "Peter Staranchuk", true))
            bills.add(Bill("BTQ", "2000", "3000", "Peter Staranchuk", false))
            bills.add(Bill("ETH", "3000000000", "4000000", "Peter Staranchuk", true))
            bills.add(Bill("STQ", "40", "5", "Peter Staranchuk", false))
            bills.add(Bill("BTQ", "5000000000", "6000000", "Peter Staranchuk", true))
            bills.add(Bill("ETH", "6000000000", "7000000", "Peter Staranchuk", true))
            emitter.onNext(bills)
            emitter.onComplete()
        }
    }
}