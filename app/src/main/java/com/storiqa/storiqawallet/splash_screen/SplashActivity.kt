package com.storiqa.storiqawallet.splash_screen

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import kotlinx.android.synthetic.main.activity_splash.*
import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.`object`.ResizeAnimation

class SplashActivity : MvpAppCompatActivity(), SplashView {

    @InjectPresenter
    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()

        ivLogo.postDelayed({
            presenter.initScreen()
        }, 3000)

    }

    override fun startShowButtonsAnimation() {
        llButtons.visibility = View.VISIBLE
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 3000

        llButtons.animation = fadeIn
        llButtons.animation.start()
    }

    override fun startResizeLogoAnimation() {
        val resizeAnimation = ResizeAnimation(
                ivLogo,
                Math.round(ivLogo.height * 0.7f),
                ivLogo.height
        )
        resizeAnimation.duration = 2000
        ivLogo.startAnimation(resizeAnimation)
    }
}
