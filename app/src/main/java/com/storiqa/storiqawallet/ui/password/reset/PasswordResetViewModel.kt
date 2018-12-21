package com.storiqa.storiqawallet.ui.password.reset

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.NonNullObservableField
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.network.errors.ErrorPresenterFields
import com.storiqa.storiqawallet.network.errors.PassMailSentDialogPresenter
import com.storiqa.storiqawallet.network.requests.ResetPasswordRequest
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.password.IPasswordRecoveryNavigator
import com.storiqa.storiqawallet.utils.isEmailValid
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PasswordResetViewModel
@Inject
constructor(navigator: IPasswordRecoveryNavigator,
            private val walletApi: WalletApi) : BaseViewModel<IPasswordRecoveryNavigator>() {

    val email = NonNullObservableField("")
    val emailError = NonNullObservableField("")

    init {
        setNavigator(navigator)

        email.addOnPropertyChanged { emailError.set("") }
    }

    fun onPasswordResetButtonClicked() {
        hideKeyboard.trigger()
        if (email.get().isEmpty())
            return

        if (isEmailValid(email.get())) {
            showLoadingDialog()
            requestResetPassword()
        } else
            emailError.set(App.res.getString(R.string.error_email_not_valid))
    }

    @SuppressLint("CheckResult")
    private fun requestResetPassword() {
        walletApi.resetPassword(ResetPasswordRequest(email.get()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess()
                }, {
                    handleError(it as Exception)
                })
    }

    private fun onSuccess() {
        hideLoadingDialog()
        val dialogPresenter = PassMailSentDialogPresenter()
        dialogPresenter.positiveButton?.onClick = ::closeActivity
        showMessageDialog(dialogPresenter)
    }

    private fun closeActivity() {
        getNavigator()?.closeActivity()
    }

    override fun showErrorFields(errorPresenter: ErrorPresenterFields) {
        errorPresenter.fieldErrors.forEach {
            it.forEach { (key, value) ->
                when (key) {
                    "email" ->
                        emailError.set(App.res.getString(value))
                }
            }
        }
    }
}