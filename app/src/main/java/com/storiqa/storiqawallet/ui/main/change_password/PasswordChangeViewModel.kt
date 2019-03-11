package com.storiqa.storiqawallet.ui.main.change_password

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.NonNullObservableField
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.data.network.WalletApi
import com.storiqa.storiqawallet.data.network.errors.DialogType
import com.storiqa.storiqawallet.data.network.errors.ErrorPresenterFields
import com.storiqa.storiqawallet.data.network.errors.PasswordChangedDialogPresenter
import com.storiqa.storiqawallet.data.network.requests.ChangePasswordRequest
import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import com.storiqa.storiqawallet.utils.SignUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PasswordChangeViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val walletApi: WalletApi,
            private val appDataStorage: IAppDataStorage,
            private val signUtil: SignUtil
) : BaseViewModel<IMainNavigator>() {

    val passwordCurrent = NonNullObservableField("")
    val passwordNew = NonNullObservableField("")
    val passwordRepeat = NonNullObservableField("")
    val passwordCurrentError = NonNullObservableField("")
    val passwordNewError = NonNullObservableField("")
    val passwordRepeatError = NonNullObservableField("")

    init {
        setNavigator(navigator)
        passwordCurrent.addOnPropertyChanged {
            passwordCurrentError.set("")
            passwordNewError.set("")
        }
        passwordNew.addOnPropertyChanged {
            passwordNewError.set("")
            passwordRepeatError.set("")
        }
        passwordRepeat.addOnPropertyChanged {
            passwordRepeatError.set("")
        }
    }

    @SuppressLint("CheckResult")
    fun onConfirmButtonClicked() {
        hideKeyboard()
        if (!validatePasswords())
            return

        showLoadingDialog()
        val email = appDataStorage.currentUserEmail
        val signHeader = signUtil.createSignHeader(email)
        val request = ChangePasswordRequest(passwordCurrent.get(), passwordNew.get())
        walletApi
                .changePassword(signHeader.timestamp, signHeader.deviceId, signHeader.signature, request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    appDataStorage.token = it
                    hideLoadingDialog()
                    showMessageDialog(PasswordChangedDialogPresenter())
                }, {
                    hideLoadingDialog()
                    handleError(it as Exception)
                })
    }

    override fun showErrorFields(errorPresenter: ErrorPresenterFields) {
        errorPresenter.fieldErrors.forEach {
            it.forEach { (key, value) ->
                when (key) {
                    "new_password" ->
                        passwordNewError.set(value)
                    "password" ->
                        passwordCurrentError.set(value)
                }
            }
        }
    }

    override fun getDialogPositiveButtonClicked(dialogType: DialogType, params: HashMap<String, String>?): () -> Unit {
        if (dialogType == DialogType.PASSWORD_CHANGED)
            return { getNavigator()?.showMenuFragment() }
        else return {}
    }

    fun validatePasswords(): Boolean {
        if (passwordCurrent.get().isNotEmpty() && passwordNew.get().isNotEmpty() && passwordCurrent.get() == passwordNew.get()) {
            passwordNewError.set(App.res.getString(R.string.error_password_new_error))
            return false
        } else if (passwordNew.get().isNotEmpty() && passwordRepeat.get().isNotEmpty() && passwordNew.get() != passwordRepeat.get()) {
            passwordRepeatError.set(App.res.getString(R.string.error_passwords_not_match))
            return false
        } else if (passwordCurrent.get().isNotEmpty() && passwordNew.get().isNotEmpty() && passwordRepeat.get().isNotEmpty()) {
            return true
        }
        return false
    }

    fun onCancelClicked() {
        getNavigator()?.showMenuFragment()
    }
}