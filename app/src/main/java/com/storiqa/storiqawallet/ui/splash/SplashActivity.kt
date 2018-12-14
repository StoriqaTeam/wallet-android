package com.storiqa.storiqawallet.ui.splash

import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivitySplashBinding
import com.storiqa.storiqawallet.ui.base.BaseActivity

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.activity_splash

    override fun getViewModelClass(): Class<SplashViewModel> = SplashViewModel::class.java
}