package com.storiqa.storiqawallet.screen_pin_code_enter

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.constants.Extras
import com.storiqa.storiqawallet.databinding.ActivityEnterPinCodeBinding
import com.storiqa.storiqawallet.enums.PinCodeEnterType
import com.storiqa.storiqawallet.objects.FingerprintHepler
import com.storiqa.storiqawallet.objects.ScreenStarter
import kotlinx.android.synthetic.main.activity_enter_pin_code.*
import android.os.VibrationEffect
import android.os.Build
import android.os.Vibrator
import org.jetbrains.anko.sdk27.coroutines.onClick

class EnterPinCodeActivity : AppCompatActivity(), EnterPinCodeView {

    lateinit var ivFingerprint : ImageView
    lateinit var viewModel : EnterPinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, EnterPinViewModelFactory(this)).get(EnterPinViewModel::class.java)

        val binding: ActivityEnterPinCodeBinding = DataBindingUtil.setContentView(this, R.layout.activity_enter_pin_code)
        binding.viewModel = viewModel

        binding.executePendingBindings()

        btnBack.setOnClickListener { onBackPressed() }

        btnLoginWithFingerprint.setOnClickListener { startFingerprintDialog(viewModel) }

        if(getPinCodeEnterType() == PinCodeEnterType.LOGIN) {
            if (FingerprintHepler(this, null).isFingerprintSetupNotAvailable()) {
                btnLoginWithFingerprint.visibility = View.GONE
            } else {
                startFingerprintDialog(viewModel)
            }
        }

        tvForgotPin.onClick {
            ScreenStarter().startLoginScreen(this@EnterPinCodeActivity)
            viewModel.eraseUserQuickLaunch()
        }
    }

    fun startFingerprintDialog(viewModel: EnterPinViewModel) {
        val view = LayoutInflater.from(this).inflate(R.layout.layout_scan_finger_for_login, null, false)
        ivFingerprint = view.findViewById<ImageView>(R.id.ivFingerprint)
        AlertDialog.Builder(this).setView(view).show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.startListenForFingerprint({
            ivFingerprint.setImageResource(R.drawable.touchid_icon)
            ScreenStarter().startMainScreen(this)
        }, {})
    }

    override fun redirectToFingerPrintSetup() {
        if(FingerprintHepler(this@EnterPinCodeActivity).isFingerprintSetupNotAvailable()) {
            ScreenStarter().startMainScreen(this@EnterPinCodeActivity)
        } else {
            ScreenStarter().startFingerprintSetupScreen(this@EnterPinCodeActivity)
            finish()
        }
    }

    override fun redirectOnMainScreen() {
        ScreenStarter().startMainScreen(this@EnterPinCodeActivity)
    }

    override fun vibrate() {
        val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            //deprecated in API 26
            v.vibrate(500)
        }
    }

    private fun getPinCodeEnterType(): PinCodeEnterType {
        val extra = intent.getSerializableExtra(Extras().pinCodeEnterType) ?: PinCodeEnterType.LOGIN
        return extra as PinCodeEnterType
    }

    override fun enterType(): PinCodeEnterType = getPinCodeEnterType()

}
