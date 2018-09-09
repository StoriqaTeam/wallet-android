package com.storiqa.storiqawallet.screen_register

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Button
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.objects.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.layout_password_enter.*
import kotlinx.android.synthetic.main.sotial_network_sign_in_footer.*

class RegisterActivity : MvpAppCompatActivity(), RegisterView {

    @InjectPresenter
    lateinit var presenter : RegisterPresenter

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

}
