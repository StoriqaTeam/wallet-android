package com.storiqa.storiqawallet.login_screen

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.storiqa.storiqawallet.R
import kotlinx.android.synthetic.main.activity_login.*
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers

class LoginActivity : MvpAppCompatActivity(), LoginView {

    @InjectPresenter
    lateinit var presenter : LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ivShowPassword.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> presenter.onShowPasswordPressed()
                MotionEvent.ACTION_UP -> presenter.onShowPasswordButtonReleased()
                else -> false
            }
        }

        RxTextView.afterTextChangeEvents(etEmail).skipInitialValue().observeOn(AndroidSchedulers.mainThread()).subscribe {
            presenter.onTextChanged(etEmail.text.toString(), etPassword.text.toString())
        }

        RxTextView.afterTextChangeEvents(etPassword).skipInitialValue().observeOn(AndroidSchedulers.mainThread()).subscribe {
            presenter.onTextChanged(etEmail.text.toString(), etPassword.text.toString())
        }

        btnSignIn.setOnClickListener {
            presenter.onSignInButtonClicked(etEmail.text.toString(), etPassword.text.toString())
        }
    }

    override fun startMainScreen() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun enableSignInButton() {
        btnSignIn.isEnabled = true
        btnSignIn.setTextColor(ResourcesCompat.getColor(resources, android.R.color.white, null))
    }

    override fun disableSignInButton() {
        btnSignIn.isEnabled = false
        btnSignIn.setTextColor(ResourcesCompat.getColor(resources, R.color.disableButton, null))
    }

    override fun hideEmailError() {
        tilEmail.error = null
    }

    override fun hidePasswordError() {
        tilPassword.error = null
    }

    override fun showGeneralError() {
        Toast.makeText(this, getString(R.string.errorVerification), Toast.LENGTH_LONG).show()
    }

    override fun moveInputAtTheEnd() {
        etPassword.setSelection(etPassword.text.length)
    }

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

    override fun showProgressBar() {
        pbLoading.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        pbLoading.visibility = View.GONE
    }
}
