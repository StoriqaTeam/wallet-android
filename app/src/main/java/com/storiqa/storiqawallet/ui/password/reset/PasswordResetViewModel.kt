package com.storiqa.storiqawallet.ui.password.reset

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.network.requests.ResetPasswordRequest
import com.storiqa.storiqawallet.utils.isEmailValid
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PasswordResetViewModel : ViewModel() {

    private val storiqaApi = WalletApi.Factory().getInstance()

    val email = ObservableField<String>("")
    val emailError = ObservableField<String>("")

    val hideKeyboard = SingleLiveEvent<Void>()

    init {
        email.addOnPropertyChanged { emailError.set("") }
    }

    fun onPasswordResetButtonClicked() {
        hideKeyboard.call()
        if (isEmailValid(email.get()!!))
            requestResetPassword()
        else
            emailError.set(App.getStringFromResources(R.string.error_email_not_valid))
    }

    @SuppressLint("CheckResult")
    private fun requestResetPassword() {
        storiqaApi.resetPassword(ResetPasswordRequest(email.get()!!))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    print("nice")
                }, {
                    it.printStackTrace()

                    print("not nice")
                })
    }
}