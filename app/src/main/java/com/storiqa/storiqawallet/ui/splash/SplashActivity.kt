package com.storiqa.storiqawallet.ui.splash

import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivitySplashBinding
import com.storiqa.storiqawallet.ui.base.BaseActivity

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_splash

}