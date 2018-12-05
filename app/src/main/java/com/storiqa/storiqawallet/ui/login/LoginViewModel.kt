package com.storiqa.storiqawallet.ui.login

import android.databinding.ObservableField
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.IError
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.network.common.RequestHeaders
import com.storiqa.storiqawallet.network.providers.ILoginNetworkProvider
import com.storiqa.storiqawallet.network.providers.LoginError
import com.storiqa.storiqawallet.network.requests.LoginRequest
import com.storiqa.storiqawallet.network.responses.TokenResponse
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.utils.getDeviceId
import com.storiqa.storiqawallet.utils.getSign
import com.storiqa.storiqawallet.utils.getTimestamp
import com.storiqa.storiqawallet.utils.isEmailValid

class LoginViewModel(
        navigator: LoginNavigator,
        private val loginNetworkProvider: ILoginNetworkProvider) : BaseViewModel<LoginNavigator>() {

    val emailError = ObservableField<String>("")
    val passwordError = ObservableField<String>("")
    val email = ObservableField<String>("")
    val password = ObservableField<String>("")

    init {
        setNavigator(navigator)
        email.addOnPropertyChanged { emailError.set("") }
        password.addOnPropertyChanged { passwordError.set("") }
    }

    fun onSignInButtonClicked() {
        hideKeyboard()
        passwordError.set("")
        emailError.set("")
        if (isEmailValid(email.get()!!)) {
            requestLogIn()
            showLoadingDialog()
        } else
            emailError.set(App.getStringFromResources(R.string.error_email_not_valid))
    }

    fun onRegistrationButtonClicked() {
        getNavigator()?.openRegistrationActivity()
    }

    fun onPasswordRecoveryButtonClicked() {
        getNavigator()?.openPasswordRecoveryActivity()
    }

    private fun requestLogIn() {
        val timestamp = getTimestamp()

        val deviceId = getDeviceId()
        val deviceOs = "25"
        val sign = getSign(timestamp, deviceId)!!

        val requestHeaders = RequestHeaders(timestamp, deviceId, sign)
        val loginRequest = LoginRequest(email.get()!!, password.get()!!, deviceOs, deviceId)

        loginNetworkProvider.requestLogIn(requestHeaders, loginRequest,
                { onSuccess(it) }, { onFailure(it) })
    }

    private fun onSuccess(token: TokenResponse?) {

        getNavigator()?.openMainActivity()//change
        hideLoadingDialog()
    }

    private fun onFailure(error: IError) {
        when (error) {
            LoginError.EMAIL_NOT_VALID ->
                emailError.set(App.getStringFromResources(R.string.error_email_not_valid))
            LoginError.EMAIL_NOT_EXIST ->
                emailError.set(App.getStringFromResources(R.string.error_email_not_exist))
            LoginError.PASS_WRONG ->
                passwordError.set(App.getStringFromResources(R.string.error_password_wrong_pass))
            LoginError.SERVER_ERROR -> {//TODO show dialog
            }
            LoginError.DEVICE_NOT_EXIST -> {//TODO request for attach
            }
        }
        hideLoadingDialog()
    }

}
