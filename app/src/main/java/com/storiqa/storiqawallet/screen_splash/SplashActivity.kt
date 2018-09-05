package com.storiqa.storiqawallet.screen_splash

import android.os.Bundle
import android.view.animation.AlphaAnimation
import kotlinx.android.synthetic.main.activity_splash.*
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.objects.ResizeAnimation
import com.storiqa.storiqawallet.objects.ScreenStarter
import android.animation.ObjectAnimator
import android.util.TypedValue
import android.view.animation.AccelerateInterpolator


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

        presenter.redirectIfUserSawSplash()
    }

    override fun startRegisterScreen() {
        ScreenStarter().startRegisterScreen(this)
        finish()
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
                Math.round(ivLogo.height * 0.9f), //make this view's height 90% from previous (decrease by 10%)
                ivLogo.height,
                Math.round(ivLogo.width * 0.9f),
                ivLogo.width
        )
        resizeAnimation.duration = 1 * SECOND
        resizeAnimation.delay = 1 * SECOND
        ivLogo.startAnimation(resizeAnimation)
    }

    override fun startMoveLogoUpAnimation() {
        moveViewUp(ivLogo)
        moveViewUp(purpleSpot)
        moveViewUp(blueSpot)
    }

    fun moveViewUp(view : View) {
        val seventyDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70f, resources.displayMetrics)

        val animation = ObjectAnimator.ofFloat(view, "translationY", -seventyDp)
        animation.interpolator = AccelerateInterpolator()
        animation.duration = 1 * SECOND
        animation.start()
    }
}
