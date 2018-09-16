package com.storiqa.storiqawallet.screen_main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.storiqa.storiqawallet.enums.Screen
import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.objects.Transaction
import com.storiqa.storiqawallet.repositories.BillsRepository
import com.storiqa.storiqawallet.repositories.CurrencyConverterRepository
import com.storiqa.storiqawallet.repositories.TransactionRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import java.math.BigDecimal

class MainActivityViewModel : ViewModel() {

    var tokenType = ObservableField<String>("STQ")
    val transactions = MutableLiveData<Array<Transaction>>()
    val selectedScreen = MutableLiveData<Screen>()
    val bills = MutableLiveData<Array<Bill>>()
    val amount = ObservableField<BigDecimal>(BigDecimal(0))
    var goBack : () -> Unit = {}
    var selectedBillId : String = ""
    var loadBillInfo : (billId : String)-> Unit = {}
    var amountInSTQ = MutableLiveData<String>()

    fun openMyWalletScreen() {
        BillsRepository().getBills().subscribe { loadedBills ->
            bills.value = loadedBills
            if(selectedBillId.isEmpty()) {
                selectedBillId = bills.value!![0].id
            }
            selectedScreen.value = Screen.MY_WALLET
        }
    }

    fun updateTransactionList(idOfSelectedBill : String, limit : Int? = null) {
        TransactionRepository().getTransactions(idOfSelectedBill, limit).subscribe { newTransactions ->
            transactions.value = newTransactions
        }
    }

    fun selectScreen(screen : Screen) {
        selectedScreen.value = screen
    }

    fun refreshAmountInStq(tokenType : String, amountInCurrency: BigDecimal) {
        CurrencyConverterRepository().getLastConverCources().observeOn(AndroidSchedulers.mainThread()).subscribe {
            amountInSTQ.value = (amountInCurrency * BigDecimal(it[tokenType]!!)).toString()
        }
    }


}