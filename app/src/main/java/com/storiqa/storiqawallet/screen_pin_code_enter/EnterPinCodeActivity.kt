package com.storiqa.storiqawallet.screen_pin_code_enter

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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

class EnterPinCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProviders.of(this, EnterPinViewModelFactory(getPinCodeEnterType())).get(EnterPinViewModel::class.java)

        val binding: ActivityEnterPinCodeBinding = DataBindingUtil.setContentView(this, R.layout.activity_enter_pin_code)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        observeRedirectOnMainScreen(viewModel)
        observeRedirectOnFingerprintSetup(viewModel)
        btnBack.setOnClickListener { onBackPressed() }

        btnLoginWithFingerprint.setOnClickListener {
            startFingerprintDialog(viewModel)
        }

        if(getPinCodeEnterType() == PinCodeEnterType.LOGIN) {
            if (FingerprintHepler(this, null).isFingerprintSetupNotAvailable()) {
                btnLoginWithFingerprint.visibility = View.GONE
            } else {
                startFingerprintDialog(viewModel)
            }
        }
    }

    fun startFingerprintDialog(viewModel: EnterPinViewModel) {
        val view = LayoutInflater.from(this).inflate(R.layout.layout_scan_finger_for_login, null, false)
        val ivFingerprint = view.findViewById<ImageView>(R.id.ivFingerprint)
        AlertDialog.Builder(this).setView(view).show()

        viewModel.startListenForFingerprint({
            ivFingerprint.setImageResource(R.drawable.fingerprint_blue)
            ScreenStarter().startMainScreen(this)
        }, {})
    }

    private fun observeRedirectOnFingerprintSetup(viewModel: EnterPinViewModel) {
        viewModel.shouldRedirectToFingerPrintSetup.observe(this, Observer<Boolean> { shouldRedirectToFingerPrintSetup ->
            if (shouldRedirectToFingerPrintSetup!!) {
                if(FingerprintHepler(this@EnterPinCodeActivity).isFingerprintSetupNotAvailable()) {
                    ScreenStarter().startMainScreen(this@EnterPinCodeActivity)
                } else {
                    ScreenStarter().startFingerprintSetupScreen(this@EnterPinCodeActivity)
                    finish()
                }
            }
        })
    }

    private fun observeRedirectOnMainScreen(viewModel: EnterPinViewModel) {
        viewModel.shouldRedirectToMainScreen.observe(this, Observer<Boolean> { shouldRedirectToMainScreen ->
            if (shouldRedirectToMainScreen!!) {
                ScreenStarter().startMainScreen(this@EnterPinCodeActivity)
            }
        })
    }

    private fun getPinCodeEnterType(): PinCodeEnterType {
        val extra = intent.getSerializableExtra(Extras().pinCodeEnterType) ?: PinCodeEnterType.LOGIN
        return extra as PinCodeEnterType
    }
}
