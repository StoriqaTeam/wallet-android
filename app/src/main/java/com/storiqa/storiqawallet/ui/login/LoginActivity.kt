package com.storiqa.storiqawallet.ui.login

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivityLoginBinding
import com.storiqa.storiqawallet.objects.GoogleAuthFlow
import com.storiqa.storiqawallet.ui.base.BaseActivity


class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    private lateinit var googleAuthFlow: GoogleAuthFlow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*val binding: ActivityLoginBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_login)*/
        //binding.viewModel = loginViewModel

        /*loginViaGoogle.setOnClickListener {
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
        })*/
        //FacebookSdk.sdkInitialize(this.applicationContext)
    }

    override fun subscribeEvents() {
        super.subscribeEvents()

        viewModel.requestLoginViaFacebook.observe(this, Observer { requestLoginViaFacebook() })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        viewModel.facebookAuthHelper.onActivityResult(requestCode, resultCode, data)
    }

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.activity_login

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    private fun requestLoginViaFacebook() {
        viewModel.facebookAuthHelper.requestLogIn(this)
    }
}