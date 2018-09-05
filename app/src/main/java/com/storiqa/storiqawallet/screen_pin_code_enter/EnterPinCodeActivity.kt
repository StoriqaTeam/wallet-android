package com.storiqa.storiqawallet.screen_pin_code_enter

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.constants.Extras
import com.storiqa.storiqawallet.databinding.ActivityEnterPinCodeBinding
import com.storiqa.storiqawallet.enums.PinCodeEnterType
import com.storiqa.storiqawallet.objects.ScreenStarter

class EnterPinCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProviders.of(this, EnterPinViewModelFactory(getPinCodeEnterType())).get(EnterPinViewModel::class.java)

        val binding: ActivityEnterPinCodeBinding = DataBindingUtil.setContentView(this, R.layout.activity_enter_pin_code)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        observeRedirectOnMainScreen(viewModel)
        observeRedirectOnFingerprintSetup(viewModel)
    }

    private fun observeRedirectOnFingerprintSetup(viewModel: EnterPinViewModel) {
        viewModel.shouldRedirectToFingerPrintSetup.observe(this, Observer<Boolean> { shouldRedirectToMainScreen ->
            if (shouldRedirectToMainScreen!!) {
                ScreenStarter().startFingerprintSetupScreen(this@EnterPinCodeActivity)
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
