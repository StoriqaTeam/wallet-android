package com.storiqa.storiqawallet.screen_scan_finger

import android.annotation.TargetApi
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivityScanFingerBinding
import com.storiqa.storiqawallet.objects.ScreenStarter
import kotlinx.android.synthetic.main.activity_scan_finger.*

class ScanFingerActivity : AppCompatActivity() {

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProviders.of(this).get(ScanFingerViewModel::class.java)
        val binding: ActivityScanFingerBinding = DataBindingUtil.setContentView(this, R.layout.activity_scan_finger)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        viewModel.startListenForFingerprint()

        btnConfirm.setOnClickListener { ScreenStarter().startMainScreen(this) }

        btnGoBack.setOnClickListener { onBackPressed() }
    }

}

