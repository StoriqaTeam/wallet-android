package com.storiqa.storiqawallet.screen_main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.storiqa.storiqawallet.objects.Bill

class MainViewModel : ViewModel() {

    val billsRepository = BillsRepository()
    val bills = MutableLiveData<List<Bill>>()

    fun refreshBillInfo() {
        billsRepository.getBills().subscribe({ newBills ->
            bills.value = newBills
        }, {
            bills.value = arrayListOf()
        })
    }

}