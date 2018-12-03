package com.storiqa.storiqawallet.ui.login

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.constants.RequestCodes
import com.storiqa.storiqawallet.databinding.ActivityLoginBinding
import com.storiqa.storiqawallet.objects.FacebookAuthFlow
import com.storiqa.storiqawallet.objects.GeneralErrorDialogHelper
import com.storiqa.storiqawallet.objects.GoogleAuthFlow
import com.storiqa.storiqawallet.screen_main.MainActivity
import com.storiqa.storiqawallet.screen_pin_code_enter.EnterPinCodeActivity
import com.storiqa.storiqawallet.screen_register.RegisterActivity
import com.storiqa.storiqawallet.ui.password.PasswordRecoveryActivity
import kotlinx.android.synthetic.main.social_network_sign_in.*


class LoginActivity : AppCompatActivity(), LoginNavigator {

    private lateinit var viewModel: LoginViewModel

    private lateinit var googleAuthFlow: GoogleAuthFlow

    private lateinit var facebookAuthFlow: FacebookAuthFlow

    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = obtainViewModel()
        viewModel.setNavigator(this)
        val binding: ActivityLoginBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel

        loginViaGoogle.setOnClickListener {
            googleAuthFlow = GoogleAuthFlow(this@LoginActivity, {
                println(it)
                println()
            }, {
                GeneralErrorDialogHelper(this).show {
                    loginViaGoogle.performClick()
                }
            })
            googleAuthFlow.performLogin()
        }

        facebookAuthFlow = FacebookAuthFlow(this, loginViaFB, {
            println(it)
            println()
        }, {
            GeneralErrorDialogHelper(this).show {
                loginViaFB.performClick()
            }
        })

        initObservers()
    }

    private fun initObservers() {
        viewModel.isLoading.addOnPropertyChanged {
            if (it.get()!!)
                showLoadingDialog(this)
            else
                hideLoadingDialog()
        }

        viewModel.hideKeyboard.observe(this, Observer { hideKeyboard() })
    }

    private fun obtainViewModel(): LoginViewModel =
            ViewModelProviders.of(this).get(LoginViewModel::class.java)

    override fun openRegistrationActivity() {
        openActivity(RegisterActivity::class.java)
    }

    override fun openPasswordRecoveryActivity() {
        openActivity(PasswordRecoveryActivity::class.java)
    }

    override fun openMainActivity() {
        openActivity(MainActivity::class.java)
    }

    private fun openActivity(clsActivity: Class<*>) {
        val intent = Intent(this, clsActivity)
        startActivity(intent)

        if (clsActivity == EnterPinCodeActivity::class.java)
            finish()
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing()) {
            loadingDialog!!.cancel()
        }
    }

    private fun showLoadingDialog(context: Context) {
        loadingDialog = Dialog(context)
        loadingDialog!!.show()
        loadingDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialog!!.setContentView(R.layout.progress_dialog)
        loadingDialog!!.setCancelable(false)
        loadingDialog!!.setCanceledOnTouchOutside(false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RequestCodes().authorizationGoogle ||
                requestCode == RequestCodes().accountGoogle) {
            googleAuthFlow.handleOnActivityResult(requestCode, resultCode, data)
        } else {
            facebookAuthFlow.handleOnActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}