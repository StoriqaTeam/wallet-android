package com.storiqa.storiqawallet.ui.splash

import android.os.Bundle
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivitySplashBinding
import com.storiqa.storiqawallet.ui.base.BaseActivity

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel, ISplashNavigator>() {

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.activity_splash

    override fun getViewModelClass(): Class<SplashViewModel> = SplashViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setNavigator(navigator)

        viewModel.checkLoggedIn()
    }
}