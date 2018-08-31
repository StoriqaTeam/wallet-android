package com.storiqa.storiqawallet.register_screen

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.storiqa.storiqawallet.R
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

        RxTextView.afterTextChangeEvents(etFirstName).skipInitialValue().observeOn(AndroidSchedulers.mainThread())
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
