package com.storiqa.storiqawallet.screen_login

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import com.storiqa.storiqawallet.constants.RequestCodes
import com.storiqa.storiqawallet.databinding.ActivityLoginBinding
import com.storiqa.storiqawallet.objects.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.sotial_network_sign_in_footer.*
import com.storiqa.storiqawallet.R

class LoginActivity : AppCompatActivity(), LoginView {

    lateinit var buttonStateSwitcher: ButtonStateSwitcherFor

    lateinit var googleAuthFlow: GoogleAuthFlow

    lateinit var facebookAuthFlow: FacebookAuthFlow

    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, LoginViewModelFactory(this)).get(LoginViewModel::class.java)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel.redirectIfAlternativeLoginSetted()

        btnGoogleLogin.setOnClickListener {
            googleAuthFlow = GoogleAuthFlow(this@LoginActivity, {
                viewModel.requestTokenFromGoogleAccount(it)
            }, {
                GeneralErrorDialogHelper(this).show {
                    btnGoogleLogin.performClick()
                }
            })
            googleAuthFlow.performLogin()
        }

        facebookAuthFlow = FacebookAuthFlow(this, fb_login_button, {
            viewModel.requestTokenFromFacebookAccount(it)
        }, {
            GeneralErrorDialogHelper(this).show {
                fb_login_button.performClick()
            }
        })

        RxTextView.textChangeEvents(etEmail).subscribe { viewModel.updateFields() }
        RxTextView.textChangeEvents(etPassword).subscribe { viewModel.updateFields() }

    }

    override fun openPinCodeEnterSceenForLogin() {
        ScreenStarter().startEnterPinCodeScreenForLogin(this)
        finish()
    }


    override fun openRecoverPasswordScreen() = ScreenStarter().startRecoverPasswordScreen(this)

    override fun showSignInError() {
        GeneralErrorDialogHelper(this).show {
            viewModel.onSignInButtonClicked()
        }
    }

    override fun startRegisterScreen() = ScreenStarter().startRegisterScreen(this)

    override fun startQuickLaunchScreen() {
        ScreenStarter().startQuickStartScreen(this)
        finish()
    }

    override fun startMainScreen() {
        ScreenStarter().startMainScreen(this)
    }

    override fun showGeneralError() =
            Toast.makeText(this, getString(R.string.errorVerification), Toast.LENGTH_LONG).show()

    override fun hidePassword() {
        etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    override fun showPassword() {
        etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
    }

    override fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun showProgressBar() {
        pbLoading.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        pbLoading.visibility = View.GONE
    }

    override fun getEmail(): String =  etEmail.text.toString()

    override fun getPassword(): String =  etPassword.text.toString()

    override fun disableSignInButton() = buttonStateSwitcher.disableButton()

    override fun enableSignInButton() = buttonStateSwitcher.enableButton()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RequestCodes().authorizationGoogle || requestCode == RequestCodes().accountGoogle) {
            googleAuthFlow.handleOnActivityResult(requestCode, resultCode, data)
        } else {
            facebookAuthFlow.handleOnActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}
