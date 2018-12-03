package com.storiqa.storiqawallet.ui.login

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.google.gson.Gson
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.StoriqaApp
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.constants.EMAIL_NOT_EXIST
import com.storiqa.storiqawallet.constants.EMAIL_NOT_VALID
import com.storiqa.storiqawallet.constants.PASS_WRONG
import com.storiqa.storiqawallet.network.StoriqaApi
import com.storiqa.storiqawallet.network.requests.LoginRequest
import com.storiqa.storiqawallet.network.responses.LoginErrorResponse
import com.storiqa.storiqawallet.network.responses.TokenResponse
import com.storiqa.storiqawallet.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.lang.ref.WeakReference

class LoginViewModel : ViewModel() {

    private val storiqaApi = StoriqaApi.Factory().getInstance()

    val isLoading = ObservableField<Boolean>(false)
    val emailError = ObservableField<String>("")
    val passwordError = ObservableField<String>("")
    val email = ObservableField<String>("")
    val password = ObservableField<String>("")

    val hideKeyboard = SingleLiveEvent<Void>()

    init {
        email.addOnPropertyChanged { emailError.set("") }
        password.addOnPropertyChanged { passwordError.set("") }
    }

    private var refNavigator: WeakReference<LoginNavigator>? = null

    fun setNavigator(loginNavigator: LoginNavigator) {
        refNavigator = WeakReference(loginNavigator)
    }

    fun onSignInButtonClicked() {
        hideKeyboard.call()
        passwordError.set("")
        emailError.set("")
        if (isEmailValid(email.get()!!)) {
            requestLogIn()
            isLoading.set(true)
        } else
            emailError.set(StoriqaApp.getStringFromRecources(R.string.error_email_not_valid))
    }

    fun onRegistrationButtonClicked() {
        refNavigator?.get()?.openRegistrationActivity()
    }

    fun onPasswordRecoveryButtonClicked() {
        refNavigator?.get()?.openPasswordRecoveryActivity()
    }

    @SuppressLint("CheckResult")
    private fun requestLogIn() {
        val timestamp = getTimestamp()
        val deviceId = getDeviceId()
        storiqaApi
                .login(timestamp, deviceId, getSign(timestamp, deviceId)!!,
                        LoginRequest(email.get()!!, password.get()!!, getDeviceOs(), getDeviceId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    handleResponse(it)

                }, {
                    it.printStackTrace()
                    isLoading.set(false)
                    print("not nice")
                })
    }

    private fun handleResponse(response: Response<TokenResponse>) {
        when (response.code()) {
            200 -> refNavigator?.get()?.openMainActivity()
            422 -> {
                val gson = Gson()
                val loginErrorResponse = gson.fromJson(response.errorBody()?.string(),
                        LoginErrorResponse::class.java)

                val emailErrorCode = loginErrorResponse.email?.get(0)?.message
                when {
                    emailErrorCode.equals(EMAIL_NOT_VALID) ->
                        emailError.set(StoriqaApp.getStringFromRecources(R.string.error_email_not_valid))

                    emailErrorCode.equals(EMAIL_NOT_EXIST) ->
                        emailError.set(StoriqaApp.getStringFromRecources(R.string.error_email_not_exist))
                }

                val passwordErrorCode = loginErrorResponse.password?.get(0)?.code
                if (passwordErrorCode.equals(PASS_WRONG))
                    passwordError.set(StoriqaApp.getStringFromRecources(R.string.error_password_wrong_pass))

                //if (loginErrorResponse.device?.get(0)?.equals(DEVICE_NOT_EXIST))
                ;//TODO request to attach device
            }
            500 -> {//TODO show dialog
            }
        }
        isLoading.set(false)
    }
}
