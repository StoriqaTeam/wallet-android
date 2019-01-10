package com.storiqa.storiqawallet.ui.login

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.NonNullObservableField
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.data.IAppDataStorage
import com.storiqa.storiqawallet.data.IUserDataStorage
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.network.errors.*
import com.storiqa.storiqawallet.network.requests.*
import com.storiqa.storiqawallet.network.responses.UserInfoResponse
import com.storiqa.storiqawallet.socialnetworks.FacebookAuthHelper
import com.storiqa.storiqawallet.socialnetworks.GoogleAuthHelper
import com.storiqa.storiqawallet.socialnetworks.SocialNetworksViewModel
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.utils.SignUtil
import com.storiqa.storiqawallet.utils.getDeviceOs
import com.storiqa.storiqawallet.utils.isEmailValid
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel
@Inject
constructor(navigator: ILoginNavigator,
            val facebookAuthHelper: FacebookAuthHelper,
            val googleAuthHelper: GoogleAuthHelper,
            private val walletApi: WalletApi,
            private val userData: IUserDataStorage,
            private val appData: IAppDataStorage,
            private val signUtil: SignUtil) :
        BaseViewModel<ILoginNavigator>(), SocialNetworksViewModel {

    val emailError = NonNullObservableField("")
    val passwordError = NonNullObservableField("")
    val email = NonNullObservableField("")
    val password = NonNullObservableField("")

    val requestLoginViaFacebook = SingleLiveEvent<Boolean>()
    val requestLoginViaGoogle = SingleLiveEvent<Boolean>()

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
        val signHeader = signUtil.createSignHeader(email.get())
        val loginRequest = LoginRequest(email.get(), password.get(), getDeviceOs(), signHeader.deviceId)

        walletApi
                .login(signHeader.timestamp, signHeader.deviceId, signHeader.signature, loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onTokenReceived(it.token)
                }, {
                    handleError(it as Exception)
                })
    }

    @SuppressLint("CheckResult")
    private fun requestLogInByOauth(token: String, provider: String) {
        val signHeader = signUtil.createSignHeader(email.get())
        val request = LoginByOauthRequest(token, provider, getDeviceOs(), signHeader.deviceId)

        walletApi
                .loginByOauth(signHeader.timestamp, signHeader.deviceId, signHeader.signature, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onTokenReceived(it.token)
                }, {
                    handleError(it as Exception)
                })
    }

    private fun onTokenReceived(token: String) {
        appData.token = token
        val bearer = "Bearer $token"
        requestUserInfo(bearer)
    }

    @SuppressLint("CheckResult")
    private fun requestUserInfo(token: String) {
        val signHeader = signUtil.createSignHeader(email.get())

        walletApi
                .getUserInfo(signHeader.timestamp, signHeader.deviceId, signHeader.signature, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    saveUserInfo(it)
                }, {
                    handleError(it as Exception)
                })
    }

    private fun saveUserInfo(userInfo: UserInfoResponse) {
        userData.id = userInfo.id
        userData.email = userInfo.email
        userData.firstName = userInfo.firstName
        userData.lastName = userInfo.lastName
        appData.currentUserEmail = userInfo.email
        getNavigator()?.openQuickLaunchQuestionActivity()
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

    override fun getDialogPositiveButtonClicked(dialogType: DialogType,
                                                params: HashMap<String, String>?): () -> Unit {
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

        val signHeader = signUtil.createSignHeader(email.get())
        val userId = params?.get("user_id")?.toInt()
                ?: throw Exception("Not found id in params")

        val addDeviceRequest = AddDeviceRequest(userId, appData.deviceOs, signHeader.deviceId, signHeader.pubKeyHex)

        walletApi
                .addDevice(signHeader.timestamp, signHeader.deviceId, signHeader.signature, addDeviceRequest)
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
                .confirmAddingDevice(ConfirmAddingDeviceRequest(token, appData.deviceId))
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
                onSuccess = {
                    showLoadingDialog()
                    requestLogInByOauth(it, "facebook")
                },
                onError = { handleError(it) })

        requestLoginViaFacebook.trigger()
    }

    override fun onGoogleLoginButtonClicked() {
        googleAuthHelper.registerCallback(
                onSuccess = {
                    showLoadingDialog()
                    requestLogInByOauth("ya29.GluBBkFBzDQMeMvVPsy6p1B4NFDT4FA7FbY_AqdCb_GN7dBhpi2qarFZyP1RmPBai0LsmRf-PXhjGlIjbRM7-PJ4AzNQ0ZlyoSQu3Yjc-MHPiMuEb7bf6LXYupMn", "google")
                },
                onError = { handleError(it) })

        requestLoginViaGoogle.trigger()
    }

    fun validateEmail() {
        if (email.get().isNotEmpty() && !isEmailValid(email.get()))
            emailError.set(App.res.getString(R.string.error_email_not_valid))
    }
}
