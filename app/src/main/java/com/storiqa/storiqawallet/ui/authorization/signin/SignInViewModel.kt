package com.storiqa.storiqawallet.ui.authorization.signin

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.NonNullObservableField
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.data.IAppDataStorage
import com.storiqa.storiqawallet.data.IUserDataStorage
import com.storiqa.storiqawallet.data.repository.IAccountsRepository
import com.storiqa.storiqawallet.data.repository.IRatesRepository
import com.storiqa.storiqawallet.data.repository.IUserRepository
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.network.errors.AttachDeviceMailSentDialogPresenter
import com.storiqa.storiqawallet.network.errors.DialogType
import com.storiqa.storiqawallet.network.errors.ErrorPresenterFields
import com.storiqa.storiqawallet.network.errors.RegistrationMailSentDialogPresenter
import com.storiqa.storiqawallet.network.requests.AddDeviceRequest
import com.storiqa.storiqawallet.network.requests.LoginByOauthRequest
import com.storiqa.storiqawallet.network.requests.LoginRequest
import com.storiqa.storiqawallet.network.requests.ResendConfirmEmailRequest
import com.storiqa.storiqawallet.network.responses.TokenResponse
import com.storiqa.storiqawallet.network.responses.UserInfoResponse
import com.storiqa.storiqawallet.socialnetworks.FacebookAuthHelper
import com.storiqa.storiqawallet.socialnetworks.GoogleAuthHelper
import com.storiqa.storiqawallet.socialnetworks.SocialNetworksViewModel
import com.storiqa.storiqawallet.ui.authorization.IAuthorizationNavigator
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.utils.SignUtil
import com.storiqa.storiqawallet.utils.getDeviceOs
import com.storiqa.storiqawallet.utils.isEmailValid
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SignInViewModel
@Inject
constructor(navigator: IAuthorizationNavigator,
            val facebookAuthHelper: FacebookAuthHelper,
            val googleAuthHelper: GoogleAuthHelper,
            private val accountsRepository: IAccountsRepository,
            private val userRepository: IUserRepository,
            private val ratesRepository: IRatesRepository,
            private val walletApi: WalletApi,
            private val userData: IUserDataStorage,
            private val appData: IAppDataStorage,
            private val signUtil: SignUtil) :
        BaseViewModel<IAuthorizationNavigator>(), SocialNetworksViewModel {

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
        if (isEmailValid(email.get())) {
            emailError.set("")
            //requestLogIn()
            sendRequests()
            showLoadingDialog()
        } else {
            emailError.set(App.res.getString(R.string.error_email_not_valid))
        }
    }

    fun onSubmitButtonClicked() {
        if (email.get().isNotEmpty() && password.get().isNotEmpty())
            onSignInButtonClicked()
        else
            hideKeyboard()
    }

    fun onRegistrationButtonClicked() {
        getNavigator()?.showSignUpFragment()
    }

    fun onPasswordRecoveryButtonClicked() {
        getNavigator()?.openPasswordRecoveryActivity()
    }

    @SuppressLint("CheckResult")
    private fun sendRequests() {
        requestLogIn()
                .doOnNext {
                    saveToken(it.token)
                }
                .flatMap { userRepository.updateUser(email.get()) }
                .doOnNext {
                    saveUserInfo(it)
                }
                .flatMap { accountsRepository.updateAccounts(it.id, it.email) }
                .flatMap { ratesRepository.updateRates() }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getNavigator()?.openQuickLaunchQuestionActivity()
                    getNavigator()?.closeActivity()
                    hideLoadingDialog()
                }, {
                    handleError(it as Exception)
                })
    }

    private fun saveToken(token: String) {
        appData.token = token
    }

    @SuppressLint("CheckResult")
    private fun requestLogIn(): Observable<TokenResponse> {
        val signHeader = signUtil.createSignHeader(email.get())
        val loginRequest = LoginRequest(email.get(), password.get(), getDeviceOs(), signHeader.deviceId)

        return walletApi
                .login(signHeader.timestamp, signHeader.deviceId, signHeader.signature, loginRequest)
    }

    @SuppressLint("CheckResult")
    private fun requestLogInByOauth(token: String, provider: String) { //TODO make chain of requests with rxJava
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
        //requestUserInfo(bearer)
    }

    private fun saveUserInfo(userInfo: UserInfoResponse) {
        userData.id = userInfo.id
        userData.email = userInfo.email
        userData.firstName = userInfo.firstName
        userData.lastName = userInfo.lastName
        appData.currentUserEmail = userInfo.email
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
                    hideLoadingDialog()
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

        val addDeviceRequest = AddDeviceRequest(userId, appData.deviceOs,
                signHeader.deviceId, signHeader.pubKeyHex)

        walletApi
                .addDevice(signHeader.timestamp, signHeader.deviceId,
                        signHeader.signature, addDeviceRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    hideLoadingDialog()
                    showMessageDialog(AttachDeviceMailSentDialogPresenter())
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
