package com.storiqa.storiqawallet.ui.password.setup

import android.annotation.SuppressLint
import android.databinding.ObservableField
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.network.errors.ErrorPresenterFields
import com.storiqa.storiqawallet.network.errors.PassSetUpDialogPresenter
import com.storiqa.storiqawallet.network.requests.ConfirmResetPasswordRequest
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.password.IPasswordRecoveryNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PasswordSetupViewModel
@Inject
constructor(navigator: IPasswordRecoveryNavigator,
            private val walletApi: WalletApi) : BaseViewModel<IPasswordRecoveryNavigator>() {

    val password = ObservableField<String>("")
    val passwordRepeat = ObservableField<String>("")
    val passwordError = ObservableField<String>("")
    val passwordRepeatError = ObservableField<String>("")

    lateinit var token: String

    init {
        setNavigator(navigator)
        //password.addOnPropertyChanged { passwordError.set("") }
        passwordRepeat.addOnPropertyChanged { passwordRepeatError.set("") }
    }

    fun onConfirmButtonClicked() {
        passwordError.set("")
        passwordRepeatError.set("")
        hideKeyboard.trigger()
        if (password.get() == passwordRepeat.get()) {
            showLoadingDialog()
            confirmResetPassword()
        } else
            passwordRepeatError.set(App.res.getString(R.string.error_passwords_not_match))
    }

    @SuppressLint("CheckResult")
    private fun confirmResetPassword() {
        walletApi.confirmResetPassword(ConfirmResetPasswordRequest(token, password.get()!!))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess()
                }, {
                    handleError(it as Exception)
                })
    }

    private fun onSuccess() {
        val dialogPresenter = PassSetUpDialogPresenter()
        dialogPresenter.positiveButton?.onClick = { getNavigator()?.openLoginActivity() }
        showMessageDialog(dialogPresenter)
    }

    override fun showErrorFields(errorPresenter: ErrorPresenterFields) {
        errorPresenter.fieldErrors.forEach {
            it.forEach { (key, value) ->
                when (key) {
                    "password" -> {
                        if (passwordError.get()!!.isEmpty())
                            passwordError.set(App.res.getString(value))
                        else
                            passwordError.set(passwordError.get() + "\n" + App.res.getString(value))
                    }
                }
            }
        }
    }

}