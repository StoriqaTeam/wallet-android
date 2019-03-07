package com.storiqa.storiqawallet.ui.password.setup

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.NonNullObservableField
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.data.network.WalletApi
import com.storiqa.storiqawallet.data.network.errors.DialogType
import com.storiqa.storiqawallet.data.network.errors.ErrorPresenterFields
import com.storiqa.storiqawallet.data.network.errors.PassSetUpDialogPresenter
import com.storiqa.storiqawallet.data.network.requests.ConfirmResetPasswordRequest
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.password.IPasswordRecoveryNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PasswordSetupViewModel
@Inject
constructor(navigator: IPasswordRecoveryNavigator,
            private val walletApi: WalletApi) : BaseViewModel<IPasswordRecoveryNavigator>() {

    val password = NonNullObservableField("")
    val passwordRepeat = NonNullObservableField("")
    val passwordError = NonNullObservableField("")
    val passwordRepeatError = NonNullObservableField("")

    lateinit var token: String

    init {
        setNavigator(navigator)
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
        walletApi.confirmResetPassword(ConfirmResetPasswordRequest(token, password.get()))
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
        showMessageDialog(PassSetUpDialogPresenter())
    }

    override fun getDialogPositiveButtonClicked(dialogType: DialogType, params: HashMap<String, String>?): () -> Unit {
        when (dialogType) {
            DialogType.RECOVERY_PASS_SET_UP ->
                return {
                    getNavigator()?.openLoginActivity()
                    getNavigator()?.closeActivity()
                }
            else -> return {}
        }
    }

    override fun showErrorFields(errorPresenter: ErrorPresenterFields) {
        errorPresenter.fieldErrors.forEach {
            it.forEach { (key, value) ->
                when (key) {
                    "password" -> {
                        if (passwordError.get().isEmpty())
                            passwordError.set(value)
                        else
                            passwordError.set(passwordError.get() + "\n" + value)
                    }
                }
            }
        }
    }

}