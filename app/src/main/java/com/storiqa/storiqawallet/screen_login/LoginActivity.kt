package com.storiqa.storiqawallet.screen_login

import android.app.Activity
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.storiqa.storiqawallet.R
import kotlinx.android.synthetic.main.activity_login.*
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.firebase.ui.auth.IdpResponse
import android.content.Intent
import android.util.Log
import com.storiqa.storiqawallet.constants.RequestCodes
import com.storiqa.storiqawallet.objects.*
import kotlinx.android.synthetic.main.sotial_network_sign_in_footer.*
import java.util.*


class LoginActivity : MvpAppCompatActivity(), LoginView {

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    private lateinit var passwordVisibilityModifier: PasswordVisibilityModifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        passwordVisibilityModifier = PasswordVisibilityModifier(etPassword, ivShowPassword)

        ivShowPassword.setOnClickListener { presenter.onChangePasswordVisibilityButtonClicked() }

        RxTextView.afterTextChangeEvents(etEmail).skipInitialValue().observeOn(AndroidSchedulers.mainThread()).subscribe {
            presenter.onTextChanged(etEmail.text.toString(), etPassword.text.toString())
        }

        RxTextView.afterTextChangeEvents(etPassword).skipInitialValue().observeOn(AndroidSchedulers.mainThread()).subscribe {
            presenter.onTextChanged(etEmail.text.toString(), etPassword.text.toString())
        }

        btnSignIn.setOnClickListener {
            presenter.onSignInButtonClicked(etEmail.text.toString(), etPassword.text.toString())
        }

        btnGoogleLogin.setOnClickListener {
            presenter.onGoogleLoginClicked()
        }

        btnFacebookLogin.setOnClickListener {
            presenter.onFacebookButtonClciked()
        }

        btnRegister.setOnClickListener { presenter.onRegisterButtonClicked() }

        btnForgotPassword.setOnClickListener { presenter.onForgotPasswordButtonClicked() }

    }

    override fun openRecoverPasswordScreen() = ScreenStarter().startRecoverPasswordScreen(this)

    override fun showSignInError() {
        GeneralErrorDialogHelper(this).show {
            presenter.onSignInButtonClicked(etEmail.text.toString(), etPassword.text.toString())
        }
    }

    override fun changePasswordVisibility() = passwordVisibilityModifier.changeVisibility()

    override fun startRegisterScreen() = ScreenStarter().startRegisterScreen(this)

    override fun startFacebookSignInProcess() = SocialNetworkTokenSignInHelper(this).startGoogleSignInProcess()

    override fun startGoogleSignInProcess() = SocialNetworkTokenSignInHelper(this).startFacebookSignInProcess()

    override fun startMainScreen() { /* TODO open main screen */ }

    override fun enableSignInButton() = ButtonStateSwitcher(resources, btnSignIn).enableButton()

    override fun disableSignInButton() = ButtonStateSwitcher(resources, btnSignIn).disableButton()

    override fun hideEmailError() {
        tilEmail.error = null
    }

    override fun hidePasswordError() {
        tilPassword.error = null
    }

    override fun showGeneralError() =
            Toast.makeText(this, getString(R.string.errorVerification), Toast.LENGTH_LONG).show()

    override fun hidePassword() {
        etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    override fun showPassword() {
        etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
    }

    override fun setEmailError(error: String) {
        tilEmail.error = error
    }

    override fun setPasswordError(error: String) {
        tilPassword.error = error
    }

    override fun showProgressBar() {
        pbLoading.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        pbLoading.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            FirebaseAuth.getInstance().getAccessToken(true).addOnCompleteListener {
                val userToken = it.result.token ?: ""
                if(requestCode == RequestCodes().requestGoogleSignIn) {
                    presenter.requestTokenFromGoogleAccount(userToken)
                }

                if(requestCode == RequestCodes().requestFacebookSignIn) {
                    presenter.requestTokenFromFacebookAccount(userToken)
                }
            }
        }
    }

}
