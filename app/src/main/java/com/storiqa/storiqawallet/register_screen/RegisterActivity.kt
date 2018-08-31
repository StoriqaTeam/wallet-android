package com.storiqa.storiqawallet.register_screen

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
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


        val consumer = { it: TextViewAfterTextChangeEvent? ->
            presenter.onFieldInformationChanged(
                    etFirstName.text.toString(), etLastName.text.toString(), etEmail.text.toString(),
                    etPassword.text.toString(), etRepeatPassword.text.toString()
            )
        }

        RxTextView.afterTextChangeEvents(etFirstName).skipInitialValue().observeOn(AndroidSchedulers.mainThread()).subscribe(consumer)
        RxTextView.afterTextChangeEvents(etLastName).skipInitialValue().observeOn(AndroidSchedulers.mainThread()).subscribe(consumer)
        RxTextView.afterTextChangeEvents(etEmail).skipInitialValue().observeOn(AndroidSchedulers.mainThread()).subscribe(consumer)
        RxTextView.afterTextChangeEvents(etPassword).skipInitialValue().observeOn(AndroidSchedulers.mainThread()).subscribe(consumer)
        RxTextView.afterTextChangeEvents(etRepeatPassword).skipInitialValue().observeOn(AndroidSchedulers.mainThread()).subscribe(consumer)
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
}
