package com.storiqa.storiqawallet.ui.main.receive

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.zxing.Result
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentPasswordResetBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import me.dm7.barcodescanner.zxing.ZXingScannerView


class ReceiveFragment : BaseFragment<FragmentPasswordResetBinding, ReceiveViewModel>(), ZXingScannerView.ResultHandler {

    override fun getLayoutId(): Int = R.layout.fragment_receive

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<ReceiveViewModel> = ReceiveViewModel::class.java

    private var mScannerView: ZXingScannerView? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mScannerView = ZXingScannerView(context)
//        mScannerView!!.setBackgroundColor(Color.BLUE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("Frag", "On Create Receive")
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return mScannerView
    }

    override fun onResume() {
        super.onResume()
        Log.d("Frag", "OnResume Receive")
        this.setupPermissions()
//        val scanView = mScannerView ?: return
//        scanView.setResultHandler(this)
//        scanView.startCamera()

    }

    override fun handleResult(rawResult: Result) {

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }

    private fun setupPermissions() {

        Log.d("HUI", "Enter to setup")

        val osVersion = Build.VERSION.SDK_INT
        val activity = this.activity ?: return

        if (osVersion >= Build.VERSION_CODES.LOLLIPOP) {
            val permissions = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)

            if (permissions != PackageManager.PERMISSION_GRANTED) {
                Log.d("HUI", "Permission to record denied")
            } else {
                Log.d("HUI", "Permission to record accept")
            }
        }
    }
}