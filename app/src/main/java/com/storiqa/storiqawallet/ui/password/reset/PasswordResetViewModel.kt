package com.storiqa.storiqawallet.ui.password.reset

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.StoriqaApp
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.network.StoriqaApi
import com.storiqa.storiqawallet.utils.isEmailValid

class PasswordResetViewModel : ViewModel() {

    private val storiqaApi = StoriqaApi.Factory().getInstance()

    val email = ObservableField<String>("")
    val emailError = ObservableField<String>("")

    val hideKeyboard = SingleLiveEvent<Void>()

    init {
        email.addOnPropertyChanged { emailError.set("") }
    }

    fun onPasswordResetButtonClicked() {
        hideKeyboard.call()
        if (isEmailValid(email.get()!!))
            ;
        else
            emailError.set(StoriqaApp.getStringFromRecources(R.string.error_email_not_valid))
    }
}