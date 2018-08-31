package com.storiqa.storiqawallet.register_screen

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.EditText
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.objects.ButtonStateSwitcher
import com.storiqa.storiqawallet.objects.PasswordVisibilityModifier
import com.storiqa.storiqawallet.objects.ScreenStarter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : MvpAppCompatActivity(), RegisterView {

    @InjectPresenter
    lateinit var presenter : RegisterPresenter

    lateinit var passwordVisibilityModifier: PasswordVisibilityModifier
    lateinit var repeatPasswordVisibilityModifier: PasswordVisibilityModifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnSignIn.setOnClickListener { presenter.onSignInButtonClicked() }

        passwordVisibilityModifier = PasswordVisibilityModifier(etPassword, ivShowPassword)
        repeatPasswordVisibilityModifier = PasswordVisibilityModifier(etRepeatPassword, ivShowRepeatedPassword)

        ivShowPassword.setOnClickListener { presenter.onShowPasswordButtonClicked() }
        ivShowRepeatedPassword.setOnClickListener { presenter.onShowRepeatedPasswordButtonClicked() }

        setChangeObserver(etFirstName)
        setChangeObserver(etLastName)
        setChangeObserver(etEmail)
        setChangeObserver(etPassword)
        setChangeObserver(etRepeatPassword)
        cbLicenseAgreement.setOnCheckedChangeListener { _, _ -> onInformationChanged() }

        btnSignUp.setOnClickListener { presenter.onSignUpButtonClicked(
                etFirstName.text.toString(), etLastName.text.toString(), etEmail.text.toString(),
                etPassword.text.toString(), etRepeatPassword.text.toString()
        ) }

    }

    override fun showPasswordsHaveToMatchError() {
        tilPassword.error = getString(R.string.errorPasswordHaveToMathc)
    }

    override fun hidePasswordsHaveToMatchError() {
        tilPassword.error = null
    }

    fun onInformationChanged() {
        presenter.onFieldInformationChanged(
                etFirstName.text.toString(), etLastName.text.toString(), etEmail.text.toString(),
                etPassword.text.toString(), etRepeatPassword.text.toString(), cbLicenseAgreement.isChecked
        )
    }

    fun setChangeObserver(view: EditText) {
        RxTextView.afterTextChangeEvents(view).skipInitialValue()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onInformationChanged() }
    }

    override fun enableSignUpButton() {
        ButtonStateSwitcher(resources, btnSignUp).enableButton()
    }

    override fun disableSignUpButton() {
        ButtonStateSwitcher(resources, btnSignUp).disableButton()
    }

    override fun changeRepeatedPasswordVisibility() {
        repeatPasswordVisibilityModifier.changeVisibility()
    }

    override fun changePasswordVisibility() {
        passwordVisibilityModifier.changeVisibility()
    }

    override fun startLoginScreen() {
        ScreenStarter().startLoginScreen(this)
        finish()
    }

    override fun showRegistrationSuccessDialog() {
        AlertDialog.Builder(this)
                .setView(R.layout.layout_sign_in_success_dialog)
                .show()
    }

    override fun showRegistrationError() {
        AlertDialog.Builder(this)
                .setView(R.layout.layout_sign_in_failed_dialog)
                .show()
    }
}
