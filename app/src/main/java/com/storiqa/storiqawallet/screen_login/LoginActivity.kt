package com.storiqa.storiqawallet.screen_login

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.db.AuthPreferences
import com.storiqa.storiqawallet.objects.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.sotial_network_sign_in_footer.*
import java.util.*


class LoginActivity : MvpAppCompatActivity(), LoginView {

    lateinit var authPreferences: AuthPreferences
    lateinit var accountManager: AccountManager

    private val AUTHORIZATION_CODE = 1993
    private val ACCOUNT_CODE = 1601

    private val SCOPE = "https://www.googleapis.com/auth/googletalk"

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    lateinit var buttonStateSwitcher: ButtonStateSwitcherFor

    val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        TextVisibilityModifierFor(etPassword).observeClickOn(ivShowPassword)
        buttonStateSwitcher = ButtonStateSwitcherFor(btnSignIn).observeNotEmpty(etEmail, etPassword)

        btnSignIn.setOnClickListener { presenter.onSignInButtonClicked(etEmail.text.toString(), etPassword.text.toString()) }
        btnRegister.setOnClickListener { presenter.onRegisterButtonClicked() }
        btnForgotPassword.setOnClickListener { presenter.onForgotPasswordButtonClicked() }

        presenter.redirectIfAlternativeLoginSetted()

        setupFacebookLogin()
        btnGoogleLogin.setOnClickListener {
            accountManager = AccountManager.get(this);
            authPreferences =  AuthPreferences(this);
            if (authPreferences.user != null && authPreferences.token != null) {
                presenter.requestTokenFromGoogleAccount(authPreferences.token!!)
            } else {
                chooseAccount()
            }
        }
    }

    fun setupFacebookLogin() {
        fb_login_button.setReadPermissions(Arrays.asList("email", "user_gender"))
        // Callback registration
        fb_login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                if (result != null && result.accessToken != null) {
                    presenter.requestTokenFromFacebookAccount(result.accessToken.token)
                }
            }

            override fun onCancel() {
                Log.d("", "")
            }

            override fun onError(error: FacebookException?) {
                Log.d("", "")
            }
        })
    }

    override fun openPinCodeEnterSceenForLogin() {
        ScreenStarter().startEnterPinCodeScreenForLogin(this)
        finish()
    }

    override fun startSetupLoginScreen() = ScreenStarter().startQuickStartScreen(this)

    override fun openRecoverPasswordScreen() = ScreenStarter().startRecoverPasswordScreen(this)

    override fun showSignInError() {
        GeneralErrorDialogHelper(this).show {
            presenter.onSignInButtonClicked(etEmail.text.toString(), etPassword.text.toString())
        }
    }

    override fun startRegisterScreen() = ScreenStarter().startRegisterScreen(this)

    override fun startQuickLaunchScreen() = ScreenStarter().startQuickStartScreen(this)

    override fun startFacebookSignInProcess() = SocialNetworkTokenSignInHelper(this).startFacebookSignInProcess()

    override fun startGoogleSignInProcess() = SocialNetworkTokenSignInHelper(this).startGoogleSignInProcess()

    override fun startMainScreen() = ScreenStarter().startMainScreen(this)

    override fun hideEmailError() {
        tilEmail.error = null
    }

    override fun hidePasswordError() {
        tilPassword.error = null
    }

    override fun showGeneralError() =
            Toast.makeText(this, getString(R.string.errorVerification), Toast.LENGTH_LONG).show()

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

    override fun disableSignInButton() = buttonStateSwitcher.disableButton()

    override fun enableSignInButton() = buttonStateSwitcher.enableButton()

    private fun chooseAccount() {
        // use https://github.com/frakbot/Android-AccountChooser for
        // compatibility with older devices
        val intent = AccountManager.newChooseAccountIntent(null, null,
                arrayOf("com.google"), false, null, null, null, null)
        startActivityForResult(intent, ACCOUNT_CODE)
    }

    private fun requestToken() {
		var userAccount : Account
		val user = authPreferences?.user
		for ( account in accountManager!!.getAccountsByType("com.google")) {
			if (account.name.equals(user)) {
				userAccount = account;
                accountManager.getAuthToken(userAccount, "oauth2:" + SCOPE, null, this,
                        OnTokenAcquired(), null);
				break;
			}
		}


	}

    private fun invalidateToken() {
        val accountManager = AccountManager.get(this)
        accountManager.invalidateAuthToken("com.google",
                authPreferences.token)

        authPreferences.token = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AUTHORIZATION_CODE) {
                requestToken()
            } else if (requestCode == ACCOUNT_CODE) {
                val accountName = data!!
                        .getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
                authPreferences.user = accountName

                // invalidate old tokens which might be cached. we want a fresh
                // one, which is guaranteed to work
                invalidateToken()

                requestToken()
            }
        }
    }

    private inner class OnTokenAcquired : AccountManagerCallback<Bundle> {

        override fun run(result: AccountManagerFuture<Bundle>) {
            try {
                val bundle = result.result

                if (bundle.get(AccountManager.KEY_INTENT) != null) {
                    startActivityForResult(bundle.get(AccountManager.KEY_INTENT) as Intent, AUTHORIZATION_CODE)
                } else {
                    val token = bundle.getString(AccountManager.KEY_AUTHTOKEN)
                    authPreferences.token = token
                    presenter.requestTokenFromGoogleAccount(token)
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }
    }
}
