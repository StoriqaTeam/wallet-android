package com.storiqa.storiqawallet.ui.registration

import android.annotation.SuppressLint
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.network.errors.ErrorPresenterFields
import com.storiqa.storiqawallet.network.requests.RegisterUserRequest
import com.storiqa.storiqawallet.network.responses.RegisterUserResponse
import com.storiqa.storiqawallet.socialnetworks.FacebookAuthHelper
import com.storiqa.storiqawallet.socialnetworks.SocialNetworksViewModel
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.binding.SubmitButtonCallback
import com.storiqa.storiqawallet.utils.getDeviceId
import com.storiqa.storiqawallet.utils.getSign
import com.storiqa.storiqawallet.utils.getTimestamp
import com.storiqa.storiqawallet.utils.isEmailValid
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RegistrationViewModel
@Inject
constructor(navigator: IRegistrationNavigator,
            private val walletApi: WalletApi,
            val facebookAuthHelper: FacebookAuthHelper) :
        BaseViewModel<IRegistrationNavigator>(), SocialNetworksViewModel, SubmitButtonCallback {

    val firstName = ObservableField<String>("")
    val lastName = ObservableField<String>("")
    val email = ObservableField<String>("")
    val emailError = ObservableField<String>("")
    val password = ObservableField<String>("")
    val passwordError = ObservableField<String>("")
    val passwordRepeat = ObservableField<String>("")
    val passwordRepeatError = ObservableField<String>("")
    val policyAgreement = ObservableBoolean(false)
    val signUpButtonEnabled = ObservableBoolean(false)

    init {
        setNavigator(navigator)

        firstName.addOnPropertyChanged { checkSignUpButtonEnabled() }
        lastName.addOnPropertyChanged { checkSignUpButtonEnabled() }
        email.addOnPropertyChanged {
            emailError.set("")
            checkSignUpButtonEnabled()
        }
        password.addOnPropertyChanged { checkSignUpButtonEnabled() }
        passwordRepeat.addOnPropertyChanged {
            passwordRepeatError.set("")
            checkSignUpButtonEnabled()
        }
        policyAgreement.addOnPropertyChanged { checkSignUpButtonEnabled() }
    }

    private fun checkSignUpButtonEnabled() {
        if (firstName.get()?.isNotEmpty()!! && lastName.get()?.isNotEmpty()!! &&
                email.get()?.isNotEmpty()!! && password.get()?.isNotEmpty()!! &&
                passwordRepeat.get()?.isNotEmpty()!! && policyAgreement.get() &&
                emailError.get() == "")
            signUpButtonEnabled.set(true)
        else
            signUpButtonEnabled.set(false)
    }

    fun validateEmail() {
        if (!isEmailValid(email.get()!!))
            emailError.set(App.res.getString(R.string.error_email_not_valid))
    }

    override fun onSubmitButtonClicked() {
        hideKeyboard()
        if (password.get()?.isNotEmpty()!! && passwordRepeat.get()?.isNotEmpty()!!
                && password.get() != passwordRepeat.get())
            passwordRepeatError.set(App.res.getString(R.string.error_passwords_not_match))
    }

    fun onSignUpButtonClicked() {
        hideKeyboard()
        passwordError.set("")
        if (!isEmailValid(email.get()!!))
            emailError.set(App.res.getString(R.string.error_email_not_valid))
        else if (password.get()?.isNotEmpty()!! && passwordRepeat.get()?.isNotEmpty()!!
                && password.get() != passwordRepeat.get())
            passwordRepeatError.set(App.res.getString(R.string.error_passwords_not_match))
        else {
            showLoadingDialog()
            requestRegistration()
        }
    }

    @SuppressLint("CheckResult")
    private fun requestRegistration() {
        val timestamp = getTimestamp()
        val deviceId = getDeviceId()
        val deviceOs = "25"
        val sign = getSign(timestamp, deviceId)!!
        val registerUserRequest = RegisterUserRequest(email.get()!!, "", password.get()!!,
                firstName.get()?.removeSuffix(" ")!!, lastName.get()?.removeSuffix(" ")!!, deviceOs, deviceId)

        val observableField: Observable<RegisterUserResponse> =
                walletApi.registerUser(timestamp, deviceId, sign, registerUserRequest)

        observableField
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess(it)
                }, {
                    handleError(it as Exception)
                })
    }

    private fun onSuccess(response: RegisterUserResponse) {
        hideLoadingDialog()
        println(response)
    }

    override fun showErrorFields(errorPresenter: ErrorPresenterFields) {
        errorPresenter.fieldErrors.forEach {
            it.forEach { (key, value) ->
                when (key) {
                    "email" ->
                        emailError.set(App.res.getString(value))
                    "password" ->
                        if (passwordError.get()!!.isEmpty())
                            passwordError.set(App.res.getString(value))
                        else
                            passwordError.set(passwordError.get() + "\n" + App.res.getString(value))
                }
            }
        }
    }

    fun onSignInButtonClicked() {
        getNavigator()?.openLoginActivity()
    }

    override fun onFacebookLoginButtonClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGoogleLoginButtonClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}