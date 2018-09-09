package com.storiqa.storiqawallet.screen_login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Scope
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.objects.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.sotial_network_sign_in_footer.*


class LoginActivity : MvpAppCompatActivity(), LoginView {

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    lateinit var buttonStateSwitcher: ButtonStateSwitcherFor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        TextVisibilityModifierFor(etPassword).observeClickOn(ivShowPassword)
        buttonStateSwitcher = ButtonStateSwitcherFor(btnSignIn).observeNotEmpty(etEmail, etPassword)

        btnGoogleLogin.setOnClickListener {

        }

        btnFacebookLogin.setOnClickListener { presenter.onFacebookButtonClciked() }

        btnSignIn.setOnClickListener { presenter.onSignInButtonClicked(etEmail.text.toString(), etPassword.text.toString()) }
        btnRegister.setOnClickListener { presenter.onRegisterButtonClicked() }
        btnForgotPassword.setOnClickListener { presenter.onForgotPasswordButtonClicked() }

        presenter.redirectIfAlternativeLoginSetted()

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
		val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
		if (result.isSuccess()) {
			val acct = result.getSignInAccount();
			val authCode = acct?.getServerAuthCode();
//			getAccessToken(authCode);
		}
	}

//        if (resultCode == Activity.RESULT_OK) {
//            FirebaseAuth.getInstance().getAccessToken(true).addOnCompleteListener {
//                val userToken = it.result.token ?: ""
//                if (requestCode == RequestCodes().requestGoogleSignIn) {
////                    presenter.requestTokenFromGoogleAccount(getGoogleToken())
//                }
//
//                if (requestCode == RequestCodes().requestFacebookSignIn) {
//                    presenter.requestTokenFromFacebookAccount(userToken)
//                }
//            }
//        }
    }

    override fun disableSignInButton() = buttonStateSwitcher.disableButton()

    override fun enableSignInButton() = buttonStateSwitcher.enableButton()

//    private fun getGoogleToken(result: (token: String) -> Unit) {
//        Dexter.withActivity(this).withPermissions(android.Manifest.permission.GET_ACCOUNTS, android.Manifest.permission.ACCOUNT_MANAGER)
//                .withListener(object : MultiplePermissionsListener {
//                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
////                            val am = AccountManager.get(applicationContext)
////
////                            val accounts = am.getAccountsByType(AccountType.GOOGLE)
////
////
////                            val accountManagerFuture = am.getAuthToken(accounts[0], "oauth2:https://www.googleapis.com/auth/userinfo.profile", null, this@LoginActivity, null, null)
////                            val authTokenBundle = accountManagerFuture.result
////                            result(authTokenBundle.getString(AccountManager.KEY_AUTHTOKEN)!!.toString())
//
//                        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                                .requestServerAuthCode(getString(R.string.server_client_id))
//                                .requestEmail()
//                                .requestScopes(Scope("https://www.googleapis.com/auth/youtube.readonly"))
//                                .build();
//
//                        val mApiClient = GoogleApiClient.Builder(this@LoginActivity)
//                                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                                .build();
//
//                        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mApiClient)
//                        startActivityForResult(signInIntent, 0)
//                    }
//
//                    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
//                        token?.continuePermissionRequest()
//                    }
//
//                }).check()
//    }
}
