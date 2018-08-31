package com.storiqa.storiqawallet.screen_recover_password.screen_recover_email_enter_first_step

import android.os.Bundle
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.objects.ButtonStateSwitcher
import com.storiqa.storiqawallet.objects.ScreenStarter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_recover_password.*
import kotlinx.android.synthetic.main.header.*

class RecoverPasswordActivity : MvpAppCompatActivity(), RecoverPasswordView {

    @InjectPresenter
    lateinit var presenter: RecoverPasswordPresenter

    lateinit var buttonStateSwitcher: ButtonStateSwitcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)

        RxTextView.afterTextChangeEvents(etEmail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { presenter.onEmailTextChanged(etEmail.text.toString()) }

        buttonStateSwitcher = ButtonStateSwitcher(resources, btnResetPassword)
        btnResetPassword.setOnClickListener { presenter.onResetPasswordButtonClicked(etEmail.text.toString()) }
        btnBack.setOnClickListener { presenter.onBackButtonPressed() }
    }

    override fun goBack() = onBackPressed()

    override fun openNewPasswordEnterScreen(email: String) = ScreenStarter().startNewPasswordEnterScreen(this, email)

    override fun enableResetPasswordButton() = buttonStateSwitcher.enableButton()

    override fun disableResetPasswordButton() = buttonStateSwitcher.disableButton()

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
