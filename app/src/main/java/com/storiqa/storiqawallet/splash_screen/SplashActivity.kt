package com.storiqa.storiqawallet.splash_screen

import android.os.Bundle
import android.view.animation.AlphaAnimation
import kotlinx.android.synthetic.main.activity_splash.*
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.`object`.ResizeAnimation
import com.storiqa.storiqawallet.`object`.ScreenStarter
import android.animation.ObjectAnimator
import android.view.animation.LinearInterpolator


class SplashActivity : MvpAppCompatActivity(), SplashView {

    @InjectPresenter
    lateinit var presenter: SplashPresenter

    private val SECOND = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        btnGetStarted.setOnClickListener{presenter.onGetStartedButtonClicked()}
        btnSignIn.setOnClickListener { presenter.onSignInButtonClicked() }

        ivLogo.postDelayed({
            presenter.initScreen()
        }, 2 * SECOND)
    }

    override fun startLoginScreen() {
        ScreenStarter().startLoginScreen(this)
        finish()
    }

    override fun startShowButtonsAnimation() {
        llButtons.visibility = View.VISIBLE
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 3 * SECOND

        llButtons.animation = fadeIn
        llButtons.animation.start()
    }

    override fun startResizeLogoAnimation() {
        val resizeAnimation = ResizeAnimation(
                ivLogo,
                Math.round(ivLogo.height * 0.7f), //make this view's height 70% from previous (decrease by 30%)
                ivLogo.height,
                Math.round(ivLogo.width * 0.7f),
                ivLogo.width
        )
        resizeAnimation.duration = 2 * SECOND
        ivLogo.startAnimation(resizeAnimation)
    }

    override fun startMoveLogoUpAnimation() {
        val animation = ObjectAnimator.ofFloat(ivLogo, "translationY", -200f)
        animation.interpolator = LinearInterpolator()
        animation.duration = 2 * SECOND
        animation.start()
    }
}
