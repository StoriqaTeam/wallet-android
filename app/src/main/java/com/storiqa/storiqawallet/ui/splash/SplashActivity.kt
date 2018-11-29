package com.storiqa.storiqawallet.ui.splash

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivitySplashBinding
import com.storiqa.storiqawallet.screen_login.LoginActivity
import com.storiqa.storiqawallet.screen_pin_code_enter.EnterPinCodeActivity
import com.storiqa.storiqawallet.screen_register.RegisterActivity

class SplashActivity : AppCompatActivity(), SplashNavigator {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = obtainViewModel()
        viewModel.setNavigator(this)
        viewModel.checkLoggedIn()
        val binding: ActivitySplashBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.viewModel = viewModel
    }

    fun obtainViewModel(): SplashViewModel =
            ViewModelProviders.of(this).get(SplashViewModel::class.java)

    override fun openLoginActivity() {
        openActivity(LoginActivity::class.java)
    }

    override fun openRegistrationActivity() {
        openActivity(RegisterActivity::class.java)
    }

    override fun openEnterPinActivity() {
        openActivity(EnterPinCodeActivity::class.java)
    }

    private fun openActivity(clsActivity: Class<*>) {
        val intent = Intent(this, clsActivity)
        startActivity(intent)

        if (clsActivity == EnterPinCodeActivity::class.java)
            finish()
    }
}