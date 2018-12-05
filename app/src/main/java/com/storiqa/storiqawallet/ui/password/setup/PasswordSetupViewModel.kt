package com.storiqa.storiqawallet.ui.password.setup

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.storiqa.storiqawallet.network.WalletApi

class PasswordSetupViewModel : ViewModel() {

    private val storiqaApi = WalletApi.Factory().getInstance()

    val password = ObservableField<String>("")
    val passwordRepeat = ObservableField<String>("")
    val passwordError = ObservableField<String>("")

    /*init {
        email.addOnPropertyChanged { emailError.set("") }
    }*/

    fun onConfirmButtonClicked() {

    }
}