package com.storiqa.storiqawallet.screen_recover_password.screen_recover_password_enter_second_step

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.objects.ButtonStateSwitcherFor
import com.storiqa.storiqawallet.objects.GeneralErrorDialogHelper
import com.storiqa.storiqawallet.objects.ScreenStarter
import com.storiqa.storiqawallet.objects.TextVisibilityModifierFor
import kotlinx.android.synthetic.main.activity_new_password_enter.*
import kotlinx.android.synthetic.main.layout_password_enter.*

class NewPasswordEnterActivity : MvpAppCompatActivity(), NewPasswordEnterView {

    @InjectPresenter
    lateinit var presenter: NewPasswordEnterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password_enter)

        TextVisibilityModifierFor(etPassword).observeClickOn(ivShowPassword)
        TextVisibilityModifierFor(etRepeatPassword).observeClickOn(ivShowRepeatedPassword)
        ButtonStateSwitcherFor(btnConfirmPassword).observeNotEmpty(etPassword, etRepeatPassword)

        btnBack.setOnClickListener { presenter.onBackButtonClicked() }
        btnConfirmPassword.setOnClickListener {
            presenter.onConfirmButtonClicked(etPassword.text.toString(), etRepeatPassword.text.toString(), getResetToken())
        }
    }

    override fun showProgress() {
        pbLoading.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pbLoading.visibility = View.GONE
    }

    override fun showPasswordsNotMatchError() {
        Toast.makeText(this, R.string.errorPasswordHaveToMathc, Toast.LENGTH_LONG).show()
    }

    override fun startLoginScreen() {
        ScreenStarter().startLoginScreen(this)
    }

    override fun showGeneralError() {
        GeneralErrorDialogHelper(this).show {
            presenter.onConfirmButtonClicked(etPassword.text.toString(), etRepeatPassword.text.toString(), getResetToken())
        }
    }

    private fun getResetToken() = intent.data.path.substring(intent.data.path.lastIndexOf("/") + 1)

    override fun goBack() = onBackPressed()
}
