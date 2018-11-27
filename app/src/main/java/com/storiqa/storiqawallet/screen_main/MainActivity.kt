package com.storiqa.storiqawallet.screen_main

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivityMainBinding
import com.storiqa.storiqawallet.enums.Screen
import com.storiqa.storiqawallet.screen_main.deposit.DepositFragment
import com.storiqa.storiqawallet.screen_main.exchange.ExchangeFragment
import com.storiqa.storiqawallet.screen_main.menu.MenuFragment
import com.storiqa.storiqawallet.screen_main.my_wallet.MyWalletFragment
import com.storiqa.storiqawallet.screen_main.my_wallet.WalletLastTransactionsFragment
import com.storiqa.storiqawallet.screen_main.send.ChooseRecieverFragment
import com.storiqa.storiqawallet.screen_main.send.SendFinalScreen
import com.storiqa.storiqawallet.screen_main.send.SendFragment


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
            } else {
                finish()
            }
        }

        viewModel.openSendFinalScreen = {
            supportFragmentManager.beginTransaction().replace(R.id.flWallet, SendFinalScreen()).addToBackStack("").commit()
        }

        viewModel.onScreenChanged = { newScreen ->
            when (newScreen) {
                Screen.MY_WALLET -> {
                    loadMyWalletFragment()
                }
                Screen.DEPOSIT -> {
                    loadDepositFragment()
                }
                Screen.EXCHANGE -> {
                    loadExchangeFragment()
                }
                Screen.SEND -> {
                    loadSendFragment()
                }
                Screen.MENU -> {
                    loadMenuFragment()
                }
            }
        }
    }

    private fun loadMenuFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.flWallet, MenuFragment()).addToBackStack("").commit()
    }

    private fun loadExchangeFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.flWallet, ExchangeFragment()).addToBackStack("").commit()
    }

    private fun loadDepositFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.flWallet, DepositFragment()).addToBackStack("").commit()
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
