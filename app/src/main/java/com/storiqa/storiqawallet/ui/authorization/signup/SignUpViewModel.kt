package com.storiqa.storiqawallet.ui.authorization.signup

import android.annotation.SuppressLint
import androidx.databinding.ObservableBoolean
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.NonNullObservableField
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.data.IUserDataStorage
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.network.errors.DialogType
import com.storiqa.storiqawallet.network.errors.ErrorPresenterFields
import com.storiqa.storiqawallet.network.errors.RegistrationMailSentDialogPresenter
import com.storiqa.storiqawallet.network.requests.RegisterUserRequest
import com.storiqa.storiqawallet.network.responses.RegisterUserResponse
import com.storiqa.storiqawallet.socialnetworks.FacebookAuthHelper
import com.storiqa.storiqawallet.socialnetworks.SocialNetworksViewModel
import com.storiqa.storiqawallet.ui.authorization.IAuthorizationNavigator
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.utils.SignUtil
import com.storiqa.storiqawallet.utils.getDeviceOs
import com.storiqa.storiqawallet.utils.isEmailValid
import com.storiqa.storiqawallet.utils.isUserNameValid
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SignUpViewModel
@Inject
constructor(navigator: IAuthorizationNavigator,
            val facebookAuthHelper: FacebookAuthHelper,
            private val walletApi: WalletApi,
            private val userData: IUserDataStorage,
            private val signUtil: SignUtil) :
        BaseViewModel<IAuthorizationNavigator>(), SocialNetworksViewModel {

    val firstName = NonNullObservableField("")
    val firstNameError = NonNullObservableField("")
    val lastName = NonNullObservableField("")
    val lastNameError = NonNullObservableField("")
    val email = NonNullObservableField("")
    val emailError = NonNullObservableField("")
    val password = NonNullObservableField("")
    val passwordError = NonNullObservableField("")
    val passwordRepeat = NonNullObservableField("")
    val passwordRepeatError = NonNullObservableField("")
    val policyAgreement = ObservableBoolean(false)
    val licenseAgreement = ObservableBoolean(false)
    val signUpButtonEnabled = ObservableBoolean(false)

    init {
        setNavigator(navigator)

        firstName.addOnPropertyChanged {
            firstNameError.set("")
            checkSignUpButtonEnabled()
        }
        lastName.addOnPropertyChanged {
            lastNameError.set("")
            checkSignUpButtonEnabled()
        }
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
        licenseAgreement.addOnPropertyChanged { checkSignUpButtonEnabled() }
    }

    private fun checkSignUpButtonEnabled() {
        if (firstName.get().isNotEmpty() && lastName.get().isNotEmpty() &&
                email.get().isNotEmpty() && password.get().isNotEmpty() &&
                passwordRepeat.get().isNotEmpty() && policyAgreement.get() &&
                licenseAgreement.get() && isEmailValid(email.get()) &&
                isUserNameValid(firstName.get().removeSuffix(" ")) &&
                isUserNameValid(lastName.get().removeSuffix(" ")))
            signUpButtonEnabled.set(true)
        else
            signUpButtonEnabled.set(false)
    }

    fun validateEmail() {
        if (email.get().isNotEmpty() && !isEmailValid(email.get()))
            emailError.set(App.res.getString(R.string.error_email_not_valid))
    }

    fun validateFirstName() {
        if (firstName.get().isNotEmpty() && !isUserNameValid(firstName.get().removeSuffix(" ")))
            firstNameError.set(App.res.getString(R.string.error_name_not_valid))
        else
            firstNameError.set("")
    }

    fun validateLastName() {
        if (lastName.get().isNotEmpty() && !isUserNameValid(lastName.get().removeSuffix(" ")))
            lastNameError.set(App.res.getString(R.string.error_name_not_valid))
        else
            lastNameError.set("")
    }

    fun onSubmitButtonClicked() {
        hideKeyboard()
        if (password.get().isNotEmpty() && passwordRepeat.get().isNotEmpty()
                && password.get() != passwordRepeat.get())
            passwordRepeatError.set(App.res.getString(R.string.error_passwords_not_match))
    }

    fun onSignUpButtonClicked() {
        hideKeyboard()
        passwordError.set("")
        if (!isEmailValid(email.get()))
            emailError.set(App.res.getString(R.string.error_email_not_valid))
        else if (password.get().isNotEmpty() && passwordRepeat.get().isNotEmpty()
                && password.get() != passwordRepeat.get())
            passwordRepeatError.set(App.res.getString(R.string.error_passwords_not_match))
        else {
            showLoadingDialog()
            requestRegistration()
        }
    }

    @SuppressLint("CheckResult")
    private fun requestRegistration() {
        val signHeader = signUtil.createSignHeader(email.get())
        val registerUserRequest = RegisterUserRequest(email.get(), "", password.get(),
                firstName.get().removeSuffix(" "), lastName.get().removeSuffix(" "), getDeviceOs(), signHeader.deviceId, signHeader.pubKeyHex)

        walletApi
                .registerUser(signHeader.timestamp, signHeader.deviceId, signHeader.signature, registerUserRequest)
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
        showMessageDialog(RegistrationMailSentDialogPresenter())
    }

    override fun showErrorFields(errorPresenter: ErrorPresenterFields) {
        errorPresenter.fieldErrors.forEach {
            it.forEach { (key, value) ->
                when (key) {
                    "email" ->
                        emailError.set(App.res.getString(value))
                    "password" ->
                        if (passwordError.get().isEmpty())
                            passwordError.set(App.res.getString(value))
                        else
                            passwordError.set(passwordError.get() + "\n" + App.res.getString(value))
                }
            }
        }
    }

    override fun getDialogPositiveButtonClicked(dialogType: DialogType,
                                                params: HashMap<String, String>?): () -> Unit {
        when (dialogType) {
            DialogType.REGISTRATION_MAIL_SENT -> return {
                getNavigator()?.showSignInFragment() //TODO add flags
                getNavigator()?.closeActivity()
            }
            else -> return { }
        }
    }

    fun onSignInButtonClicked() {
        getNavigator()?.showSignInFragment()
    }

    override fun onFacebookLoginButtonClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGoogleLoginButtonClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}