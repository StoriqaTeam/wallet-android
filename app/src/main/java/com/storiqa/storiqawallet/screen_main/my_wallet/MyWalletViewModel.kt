package com.storiqa.storiqawallet.screen_main.my_wallet

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.repositories.BillsRepository

class MyWalletViewModel : ViewModel() {

    val billsRepository = BillsRepository()
    val bills = MutableLiveData<Array<Bill>>()

    fun refreshBillInfo() {
        billsRepository.getBills().subscribe({ newBills ->
            bills.value = newBills
        }, {
            bills.value = arrayOf()
        })
    }

}