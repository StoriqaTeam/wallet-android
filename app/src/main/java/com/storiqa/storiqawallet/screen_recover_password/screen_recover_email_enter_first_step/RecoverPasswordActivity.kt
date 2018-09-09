package com.storiqa.storiqawallet.screen_recover_password.screen_recover_email_enter_first_step

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.objects.ButtonStateSwitcherFor
import com.storiqa.storiqawallet.objects.GeneralErrorDialogHelper
import kotlinx.android.synthetic.main.activity_recover_password.*
import android.content.Intent
import android.widget.Toast


class RecoverPasswordActivity : MvpAppCompatActivity(), RecoverPasswordView {
    @InjectPresenter
    lateinit var presenter: RecoverPasswordPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)

        ButtonStateSwitcherFor(btnResetPassword).observeNotEmpty(etEmail)
        btnResetPassword.setOnClickListener { presenter.onResetPasswordButtonClicked(etEmail.text.toString()) }
        btnBack.setOnClickListener { presenter.onBackButtonPressed() }
    }

    override fun goBack() = onBackPressed()

    override fun onNewPasswordEmailSent(email: String) {
        android.support.v7.app.AlertDialog.Builder(this).setMessage(getString(R.string.resetDialogMessage))
                .setPositiveButton(getString(R.string.openEmail)) { _: DialogInterface, i: Int ->
                    val intent = Intent(Intent.ACTION_SEND)
                    val mailer = Intent.createChooser(intent, null)

                    if(intent.resolveActivity(packageManager) != null) {
                        startActivity(mailer)
                    } else {
                        Toast.makeText(this@RecoverPasswordActivity, getString(R.string.noEmailProgram), Toast.LENGTH_LONG).show()
                    }
                }.setNegativeButton(getString(R.string.cancel)) { _: DialogInterface, i: Int ->
                    finish()
                }.show()
    }

    override fun showGeneralError() {
        GeneralErrorDialogHelper(this).show {
            presenter.onResetPasswordButtonClicked(etEmail.text.toString())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
