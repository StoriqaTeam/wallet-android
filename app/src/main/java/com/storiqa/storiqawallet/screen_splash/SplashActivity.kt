package com.storiqa.storiqawallet.screen_splash

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.LinearInterpolator
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.objects.ScreenStarter
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : MvpAppCompatActivity(), SplashView {

    @InjectPresenter
    lateinit var presenter: SplashPresenter

    private val SECOND = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        btnGetStarted.setOnClickListener { presenter.onGetStartedButtonClicked() }
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

    override fun startMoveLogoUpAnimation() {
        moveViewUp(ivLogo)
        moveViewUp(purpleSpot)
        moveViewUp(blueSpot)
    }

    private fun moveViewUp(view: View) {
        val animation = ObjectAnimator.ofFloat(view, "translationY", -dpToPx(45f))
        animation.interpolator = LinearInterpolator()
        animation.duration = 1 * SECOND
        animation.start()
    }

    private fun dpToPx(value: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, resources.displayMetrics)
}
