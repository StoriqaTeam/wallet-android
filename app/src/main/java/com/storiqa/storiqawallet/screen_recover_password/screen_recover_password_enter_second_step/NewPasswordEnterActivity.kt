package com.storiqa.storiqawallet.screen_recover_password.screen_recover_password_enter_second_step

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.objects.ButtonStateSwitcher
import com.storiqa.storiqawallet.objects.TextVisibilityModifierFor
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_new_password_enter.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.layout_password_enter.*

class NewPasswordEnterActivity : MvpAppCompatActivity(), NewPasswordEnterView {

    @InjectPresenter
    lateinit var presenter : NewPasswordEnterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password_enter)

        TextVisibilityModifierFor(etPassword).observeClickOn(ivShowPassword)
        TextVisibilityModifierFor(etRepeatPassword).observeClickOn(ivShowRepeatedPassword)

        btnBack.setOnClickListener { presenter.onBackButtonClicked() }

        RxTextView.afterTextChangeEvents(etPassword).observeOn(AndroidSchedulers.mainThread()).subscribe { presenter.onEnterChanged(etPassword.text.toString(), etRepeatPassword.text.toString()) }
        RxTextView.afterTextChangeEvents(etRepeatPassword).observeOn(AndroidSchedulers.mainThread()).subscribe { presenter.onEnterChanged(etPassword.text.toString(), etRepeatPassword.text.toString()) }

        btnConfirmPassword.setOnClickListener { presenter.onConfirmButtonClicked(etPassword.text.toString(), etRepeatPassword.text.toString()) }
    }

    override fun enableConfirmButton() = ButtonStateSwitcher(resources, btnConfirmPassword).enableButton()

    override fun disableConfirmButton() = ButtonStateSwitcher(resources, btnConfirmPassword).disableButton()

    override fun goBack() = onBackPressed()
}
