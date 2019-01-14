package com.storiqa.storiqawallet.screen_scan_finger

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivityScanFingerBinding
import com.storiqa.storiqawallet.objects.ScreenStarter
import kotlinx.android.synthetic.main.activity_scan_finger.*

class ScanFingerActivity : AppCompatActivity() {

    lateinit var viewModel: ScanFingerViewModel

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ScanFingerViewModel::class.java)
        val binding: ActivityScanFingerBinding = DataBindingUtil.setContentView(this, R.layout.activity_scan_finger)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        btnConfirm.setOnClickListener { ScreenStarter().startMainScreen(this) }

        btnGoBack.setOnClickListener { onBackPressed() }

        tvFingerprintError.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        viewModel.startListenForFingerprint()
    }
}

