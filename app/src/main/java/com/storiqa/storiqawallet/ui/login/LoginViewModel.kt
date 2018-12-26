package com.storiqa.storiqawallet.ui.login

import android.annotation.SuppressLint
import android.util.Log
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.NonNullObservableField
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.network.errors.*
import com.storiqa.storiqawallet.network.requests.*
import com.storiqa.storiqawallet.network.responses.TokenResponse
import com.storiqa.storiqawallet.socialnetworks.FacebookAuthHelper
import com.storiqa.storiqawallet.socialnetworks.SocialNetworksViewModel
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.utils.getDeviceId
import com.storiqa.storiqawallet.utils.getSign
import com.storiqa.storiqawallet.utils.getTimestamp
import com.storiqa.storiqawallet.utils.isEmailValid
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel
@Inject
constructor(navigator: ILoginNavigator,
            private val walletApi: WalletApi,
            val facebookAuthHelper: FacebookAuthHelper) :
        BaseViewModel<ILoginNavigator>(), SocialNetworksViewModel {

    val emailError = NonNullObservableField("")
    val passwordError = NonNullObservableField("")
    val email = NonNullObservableField("")
    val password = NonNullObservableField("")

    val requestLoginViaFacebook = SingleLiveEvent<Boolean>()

    init {
        setNavigator(navigator)
        email.addOnPropertyChanged { emailError.set("") }
        password.addOnPropertyChanged { passwordError.set("") }
    }

    fun onSignInButtonClicked() {
        hideKeyboard()
        passwordError.set("")
        emailError.set("")
        if (isEmailValid(email.get())) {
            requestLogIn()
            showLoadingDialog()
        }
    }

    fun onSubmitButtonClicked() {
        if (email.get().isNotEmpty() && password.get().isNotEmpty())
            onSignInButtonClicked()
        else
            hideKeyboard()
    }

    fun onRegistrationButtonClicked() {
        getNavigator()?.openRegistrationActivity()
    }

    fun onPasswordRecoveryButtonClicked() {
        getNavigator()?.openPasswordRecoveryActivity()
    }

    @SuppressLint("CheckResult")
    private fun requestLogIn() {
        val timestamp = getTimestamp()
        val deviceId = getDeviceId()
        val deviceOs = "25"
        val sign = getSign(timestamp, deviceId)!!
        val loginRequest = LoginRequest(email.get(), password.get(), deviceOs, deviceId)

        val observableField: Observable<TokenResponse> =
                walletApi
                        .login(timestamp, deviceId, sign, loginRequest)

        observableField
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onTokenReceived(it.token)
                }, {
                    handleError(it as Exception)
                })
    }

    private fun onTokenReceived(token: String) {
        //TODO save token
        getNavigator()?.openPinCodeActivity()
        hideLoadingDialog()
    }

    override fun showErrorFields(errorPresenter: ErrorPresenterFields) {
        errorPresenter.fieldErrors.forEach {
            it.forEach { (key, value) ->
                when (key) {
                    "email" ->
                        emailError.set(App.res.getString(value))
                    "password" ->
                        passwordError.set(App.res.getString(value))
                }
            }
        }
    }

    override fun getDialogPositiveButtonClicked(dialogType: DialogType, params: HashMap<String, String>?): () -> Unit {
        when (dialogType) {
            DialogType.DEVICE_NOT_ATTACHED -> return { attachDevice(params) }
            DialogType.EMAIL_NOT_VERIFIED -> return ::resendConfirmEmail
            DialogType.EMAIL_TIMEOUT -> {
                if (params != null)
                    return { attachDevice(params) }
                else
                    return ::resendConfirmEmail
            }
            else -> return { }
        }
    }

    @SuppressLint("CheckResult")
    private fun resendConfirmEmail() {
        showLoadingDialog()

        walletApi
                .resendConfirmEmail(ResendConfirmEmailRequest(email.get()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    hideKeyboard()
                    showMessageDialog(RegistrationMailSentDialogPresenter())
                }, {
                    handleError(it as Exception)
                })
    }

    @SuppressLint("CheckResult")
    private fun attachDevice(params: HashMap<String, String>?) {
        showLoadingDialog()

        val timestamp = getTimestamp()
        val deviceId = getDeviceId()
        val deviceOs = "25"
        val sign = getSign(timestamp, deviceId)!!
        val userId = params?.get("user_id")?.toInt()
                ?: throw Exception("Not found userId in params")

        val addDeviceRequest = AddDeviceRequest(userId, getDeviceId())

        walletApi
                .addDevice(timestamp, deviceId, sign, addDeviceRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    hideLoadingDialog()
                    showMessageDialog(AttachDeviceMailSentDialogPresenter())
                }, {
                    handleError(it as Exception)
                })
    }

    @SuppressLint("CheckResult")
    fun confirmEmail(token: String) {
        showLoadingDialog()

        walletApi
                .confirmEmail(ConfirmEmailRequest(token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    hideLoadingDialog()
                }, {
                    handleError(it as Exception)
                })
    }

    @SuppressLint("CheckResult")
    fun confirmAttachDevice(token: String) {
        showLoadingDialog()

        walletApi
                .confirmAddingDevice(ConfirmAddingDeviceRequest(token, getDeviceId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    hideLoadingDialog()
                    showMessageDialog(DeviceAttachedDialogPresenter())
                }, {
                    handleError(it as Exception)
                })
    }

    override fun onFacebookLoginButtonClicked() {
        facebookAuthHelper.registerCallback(
                onSuccess = { Log.d("TAGGG", "token: ${it.accessToken.token}") },
                onError = { handleError(it) })

        requestLoginViaFacebook.trigger()
    }

    override fun onGoogleLoginButtonClicked() {
        Log.d("TAGGG", "onGoogleLoginButtonClicked")
    }

    fun validateEmail() {
        if (email.get().isNotEmpty() && !isEmailValid(email.get()))
            emailError.set(App.res.getString(R.string.error_email_not_valid))
    }
}
