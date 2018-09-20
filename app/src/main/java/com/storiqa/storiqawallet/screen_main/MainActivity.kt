package com.storiqa.storiqawallet.screen_main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivityMainBinding
import com.storiqa.storiqawallet.enums.Screen
import com.storiqa.storiqawallet.screen_main.my_wallet.MyWalletFragment
import com.storiqa.storiqawallet.screen_main.my_wallet.WalletLastTransactionsFragment
import com.storiqa.storiqawallet.screen_main.send.ChooseRecieverFragment
import com.storiqa.storiqawallet.screen_main.send.SendFragment
import android.content.DialogInterface
import android.content.DialogInterface.BUTTON_NEUTRAL
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.storiqa.storiqawallet.constants.RequestCodes
import com.storiqa.storiqawallet.screen_main.send.SendFinalScreen


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel.openMyWalletScreen()


        observeBillSelection()

        viewModel.openRecieverScreen = {
            supportFragmentManager.beginTransaction().replace(R.id.flWallet, ChooseRecieverFragment()).addToBackStack("").commit()
        }

        viewModel.goBack = {
            if (supportFragmentManager.backStackEntryCount > 1) {
                supportFragmentManager.popBackStack()
            }
        }

        viewModel.openSendFinalScreen = {
            supportFragmentManager.beginTransaction().replace(R.id.flWallet, SendFinalScreen()).addToBackStack("").commit()
        }

        viewModel.onScreenChanged = { newScreen ->
            when (newScreen) {
                Screen.MY_WALLET -> { loadMyWalletFragment() }
                Screen.DEPOSIT -> { }
                Screen.EXCHANGE -> { }
                Screen.SEND -> { loadSendFragment() }
                Screen.MENU -> { }
            }
        }
    }

    fun observeBillSelection() {
        viewModel.loadBillInfo = { billId ->
            val walletTransactionsFragment = WalletLastTransactionsFragment.getInstance(billId, viewModel.bills.value!!)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.flWallet, walletTransactionsFragment)
            transaction.addToBackStack("")
            transaction.commit()
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = data?.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult")
        result?.let { viewModel.scannedQR.value = result }
    }

    private fun loadSendFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.flWallet, SendFragment()).addToBackStack("").commit()
    }

    private fun loadMyWalletFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.flWallet, MyWalletFragment.getInstance(viewModel.bills.value!!))
                .addToBackStack("").commit()
    }

    override fun onBackPressed() {
        viewModel.goBack()
    }
}
