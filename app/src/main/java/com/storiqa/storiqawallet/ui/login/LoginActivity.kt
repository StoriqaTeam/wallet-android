package com.storiqa.storiqawallet.ui.login

import android.os.Bundle
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivityLoginBinding
import com.storiqa.storiqawallet.objects.FacebookAuthFlow
import com.storiqa.storiqawallet.objects.GoogleAuthFlow
import com.storiqa.storiqawallet.ui.base.BaseActivity


class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    private lateinit var googleAuthFlow: GoogleAuthFlow

    private lateinit var facebookAuthFlow: FacebookAuthFlow

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
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RequestCodes().authorizationGoogle ||
                requestCode == RequestCodes().accountGoogle) {
            googleAuthFlow.handleOnActivityResult(requestCode, resultCode, data)
        } else {
            facebookAuthFlow.handleOnActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }*/
}