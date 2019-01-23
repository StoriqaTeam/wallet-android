package com.storiqa.storiqawallet.ui.main.send

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentPasswordResetBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_send.view.*


class SendFragment : BaseFragment<FragmentPasswordResetBinding, SendViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_send

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<SendViewModel> = SendViewModel::class.java
    

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_send, container, false)
        val scanButton = rootView.btnScan as Button

        scanButton.setOnClickListener{ onScanPressed() }
        return rootView
    }

    private fun onScanPressed() {
        Log.d("1", "ON SCAN PRESSED" )
    }

}