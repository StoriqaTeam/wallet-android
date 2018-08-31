package com.storiqa.storiqawallet.login_screen

import android.app.Activity
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
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
import com.storiqa.storiqawallet.objects.ButtonStateSwitcher
import com.storiqa.storiqawallet.objects.GeneralErrorDialogHelper
import com.storiqa.storiqawallet.objects.PasswordVisibilityModifier
import com.storiqa.storiqawallet.objects.ScreenStarter
import kotlinx.android.synthetic.main.sotial_network_sign_in_footer.*
import java.util.*


class LoginActivity : MvpAppCompatActivity(), LoginView {

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    lateinit var passwordVisibilityModifier: PasswordVisibilityModifier

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

    }

    override fun showSignInError() {
        GeneralErrorDialogHelper(this).show {
            presenter.onSignInButtonClicked(etEmail.text.toString(), etPassword.text.toString())
        }
    }

    override fun changePasswordVisibility() {
        passwordVisibilityModifier.changeVisibility()
    }

    override fun startRegisterScreen() {
        ScreenStarter().startRegisterScreen(this)
    }

    override fun startFacebookSignInProcess() {
        // Create and launch sign-in intent
        startSignInIntent(
                Arrays.asList(AuthUI.IdpConfig.FacebookBuilder().build()),
                RequestCodes().requestGoogleSignIn)

    }

    override fun startGoogleSignInProcess() {
        startSignInIntent(Arrays.asList(AuthUI.IdpConfig.GoogleBuilder().build()), RequestCodes().requestGoogleSignIn)
    }

    override fun startMainScreen() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun enableSignInButton() {
        ButtonStateSwitcher(resources, btnSignIn).enableButton()
    }

    override fun disableSignInButton() {
        ButtonStateSwitcher(resources, btnSignIn).disableButton()
    }

    override fun hideEmailError() {
        tilEmail.error = null
    }

    override fun hidePasswordError() {
        tilPassword.error = null
    }

    override fun showGeneralError() {
        Toast.makeText(this, getString(R.string.errorVerification), Toast.LENGTH_LONG).show()
    }

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

        if (requestCode == RequestCodes().requestGoogleSignIn || requestCode == RequestCodes().requestFacebookSignIn) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK && response != null) {
                FirebaseAuth.getInstance().getAccessToken(true).addOnCompleteListener {
                    val userToken = it.result.token!!
                    if (requestCode == RequestCodes().requestGoogleSignIn) {
                        presenter.requestTokenFromGoogleAccount(userToken)
                    } else if (requestCode == RequestCodes().requestFacebookSignIn) {
                        presenter.requestTokenFromFacebookAccount(userToken)
                    }
                }
            } else {
                Log.d("", "")
            }
        }
    }

    private fun startSignInIntent(providers: MutableList<AuthUI.IdpConfig>, requestCode: Int) {
        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), requestCode)
    }
}
