package com.storiqa.storiqawallet.screen_register

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Button
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.constants.RequestCodes
import com.storiqa.storiqawallet.objects.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.layout_password_enter.*
import kotlinx.android.synthetic.main.sotial_network_sign_in_footer.*

class RegisterActivity : MvpAppCompatActivity(), RegisterView {

    @InjectPresenter
    lateinit var presenter : RegisterPresenter
    lateinit var googleAuthFlow: GoogleAuthFlow
    lateinit var facebookAuthFlow : FacebookAuthFlow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        TextVisibilityModifierFor(etPassword).observeClickOn(ivShowPassword)
        TextVisibilityModifierFor(etRepeatPassword).observeClickOn(ivShowRepeatedPassword)

        ButtonStateSwitcherFor(btnSignUp)
                .observeNotEmpty(etFirstName)
                .observeNotEmpty(etLastName)
                .observeNotEmpty(etEmail)
                .observeNotEmpty(etPassword)
                .observeNotEmpty(etRepeatPassword)
                .observeIsChecked(cbLicenseAgreement)

        btnSignUp.setOnClickListener { presenter.onSignUpButtonClicked(
                etFirstName.text.toString(), etLastName.text.toString(), etEmail.text.toString(),
                etPassword.text.toString(), etRepeatPassword.text.toString()
        ) }

        btnSignIn.setOnClickListener { presenter.onSignInButtonClicked() }

        btnGoogleLogin.setOnClickListener {
            googleAuthFlow = GoogleAuthFlow(this@RegisterActivity, {
                presenter.requestTokenFromGoogleAccount(it)
            }, {
                GeneralErrorDialogHelper(this).show {
                    btnGoogleLogin.performClick()
                }
            })
            googleAuthFlow.performLogin()
        }

        facebookAuthFlow = FacebookAuthFlow(this, fb_login_button, {
            presenter.requestTokenFromFacebookAccount(it)
        }, {
            GeneralErrorDialogHelper(this).show {
                fb_login_button.performClick()
            }
        })
    }

    override fun showGoogleSignInError() {
        GeneralErrorDialogHelper(this).show {
            btnGoogleLogin.performClick()
        }
    }

    override fun showFacebookSignInError() {
        GeneralErrorDialogHelper(this).show {
            fb_login_button.performClick()
        }
    }

    override fun showPasswordsHaveToMatchError() {
        tilPassword.error = getString(R.string.errorPasswordHaveToMathc)
    }

    override fun hidePasswordsHaveToMatchError() {
        tilPassword.error = null
    }

    override fun clearErrors() =
            listOf(tilFirstName, tilLastName, tilEmail, tilPassword, tilRepeatedPassword).forEach { it.error = null }

    override fun startLoginScreen() {
        ScreenStarter().startLoginScreen(this)
        finish()
    }

    override fun showRegistrationSuccessDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.layout_sign_in_success_dialog, null, false)
        view.findViewById<Button>(R.id.btnSignIn).setOnClickListener { presenter.onSignInButtonClicked() }

        AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(view)
                .show()
    }

    override fun showRegistrationError() {
        GeneralErrorDialogHelper(this).show {
            presenter.onSignUpButtonClicked(etFirstName.text.toString(), etLastName.text.toString(), etEmail.text.toString(),
                    etPassword.text.toString(), etRepeatPassword.text.toString())
        }
    }

    override fun setPasswordError(passwordError: String) {
        tilPassword.error = passwordError
    }

    override fun setEmailError(emailError: String) {
        tilEmail.error = emailError
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == RequestCodes().authorizationGoogle || requestCode == RequestCodes().accountGoogle) {
            googleAuthFlow.handleOnActivityResult(requestCode, resultCode, data)
        } else {
            facebookAuthFlow.handleOnActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
