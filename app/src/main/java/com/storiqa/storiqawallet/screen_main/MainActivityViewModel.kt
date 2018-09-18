package com.storiqa.storiqawallet.screen_main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.storiqa.storiqawallet.enums.Screen
import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.objects.Contact
import com.storiqa.storiqawallet.objects.Transaction
import com.storiqa.storiqawallet.repositories.BillsRepository
import com.storiqa.storiqawallet.repositories.ContactsRepository
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
    var selectedBillId : String = ""
    var amountInSTQ = MutableLiveData<String>()
    var amountInCurrency = BigDecimal(0)

    var goBack : () -> Unit = {}
    var loadBillInfo : (billId : String)-> Unit = {}
    var openRecieverScreen : ()-> Unit = {}

    val wallet = ObservableField<String>("")
    val phone = ObservableField<String>("")
    val reciever = ObservableField<String>("")
    val contacts = MutableLiveData<Array<Contact>>()

    init {
        contacts.value = arrayOf()
        transactions.value = arrayOf()
    }

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
            this.amountInCurrency = amountInCurrency
        }
    }

    fun requestContacts() {
        ContactsRepository().getContacts().subscribe {newContacts ->
            contacts.value = newContacts
        }
    }

    fun clearSenderInfo() {
        wallet.set("")
        phone.set("")
        reciever.set("")
    }

    fun saveRecieverInfo(contact: Contact) {
        wallet.set(contact.wallet)
        phone.set(contact.phone)
        reciever.set(contact.name)
    }

    fun getContacts(): Array<Contact> {
        return contacts.value!!
    }

}