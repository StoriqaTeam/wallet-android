package com.storiqa.storiqawallet.screen_login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.constants.RequestCodes
import com.storiqa.storiqawallet.objects.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.sotial_network_sign_in_footer.*


class LoginActivity : MvpAppCompatActivity(), LoginView {

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    lateinit var buttonStateSwitcher: ButtonStateSwitcherFor

    lateinit var googleAuthFlow: GoogleAuthFlow

    lateinit var facebookAuthFlow : FacebookAuthFlow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        TextVisibilityModifierFor(etPassword).observeClickOn(ivShowPassword)
        buttonStateSwitcher = ButtonStateSwitcherFor(btnSignIn).observeNotEmpty(etEmail, etPassword)

        btnSignIn.setOnClickListener { presenter.onSignInButtonClicked(etEmail.text.toString(), etPassword.text.toString()) }
        btnRegister.setOnClickListener { presenter.onRegisterButtonClicked() }
        btnForgotPassword.setOnClickListener { presenter.onForgotPasswordButtonClicked() }

        presenter.redirectIfAlternativeLoginSetted()


        btnGoogleLogin.setOnClickListener {
            googleAuthFlow = GoogleAuthFlow(this@LoginActivity, {
                presenter.requestTokenFromGoogleAccount(it)
            }, {
                GeneralErrorDialogHelper(this).show {
                    btnGoogleLogin.performClick()
                }
            })
            googleAuthFlow.performLogin()
        }

        facebookAuthFlow = FacebookAuthFlow(fb_login_button, {
            presenter.requestTokenFromFacebookAccount(it)
        }, {
            GeneralErrorDialogHelper(this).show {
                fb_login_button.performClick()
            }
        })

    }


    override fun openPinCodeEnterSceenForLogin() {
        ScreenStarter().startEnterPinCodeScreenForLogin(this)
        finish()
    }

    override fun startSetupLoginScreen() = ScreenStarter().startQuickStartScreen(this)

    override fun openRecoverPasswordScreen() = ScreenStarter().startRecoverPasswordScreen(this)

    override fun showSignInError() {
        GeneralErrorDialogHelper(this).show {
            presenter.onSignInButtonClicked(etEmail.text.toString(), etPassword.text.toString())
        }
    }

    override fun startRegisterScreen() = ScreenStarter().startRegisterScreen(this)

    override fun startQuickLaunchScreen() = ScreenStarter().startQuickStartScreen(this)

    override fun startMainScreen() = ScreenStarter().startMainScreen(this)

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

    override fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun showProgressBar() {
        pbLoading.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        pbLoading.visibility = View.GONE
    }

    override fun disableSignInButton() = buttonStateSwitcher.disableButton()

    override fun enableSignInButton() = buttonStateSwitcher.enableButton()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == RequestCodes().authorizationGoogle || requestCode == RequestCodes().accountGoogle) {
            googleAuthFlow.handleOnActivityResult(requestCode, resultCode, data)
        } else {
            facebookAuthFlow.handleOnActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}
