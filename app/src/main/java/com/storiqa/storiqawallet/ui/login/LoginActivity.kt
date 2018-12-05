package com.storiqa.storiqawallet.ui.login

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivityLoginBinding
import com.storiqa.storiqawallet.di.viewModelModule
import com.storiqa.storiqawallet.objects.FacebookAuthFlow
import com.storiqa.storiqawallet.objects.GoogleAuthFlow
import com.storiqa.storiqawallet.screen_main.MainActivity
import com.storiqa.storiqawallet.screen_register.RegisterActivity
import com.storiqa.storiqawallet.ui.base.BaseActivity
import com.storiqa.storiqawallet.ui.password.PasswordRecoveryActivity
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.on


class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator {


    val kodein = Kodein {
        import(viewModelModule)

        bind<LoginActivity>() with instance(this@LoginActivity)
    }

    private val loginViewModel: LoginViewModel by kodein.on(this as Activity).instance()

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

    override fun getViewModel(): LoginViewModel {
        return loginViewModel
    }

    private fun obtainViewModel(): LoginViewModel =
            ViewModelProviders.of(this).get(LoginViewModel::class.java)

    override fun openRegistrationActivity() {
        openActivity(RegisterActivity::class.java, false)
    }

    override fun openPasswordRecoveryActivity() {
        openActivity(PasswordRecoveryActivity::class.java, false)
    }

    override fun openMainActivity() {
        openActivity(MainActivity::class.java, true)
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