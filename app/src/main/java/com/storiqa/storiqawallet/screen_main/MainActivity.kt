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
import com.storiqa.storiqawallet.screen_main.my_wallet.WalletTransactionsFragment


class MainActivity : AppCompatActivity() {

    lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel.openMyWalletScreen()

        observeScreenChange()
        observeBillSelection()

        viewModel.goBack = {
            if(supportFragmentManager.backStackEntryCount > 1) {
                supportFragmentManager.popBackStack()
            }
        }
    }

    fun observeBillSelection() {
        viewModel.selectedBillId.observe(this, object : Observer<String> {
            override fun onChanged(idOfBill: String?) {
                val walletTransactionsFragment = WalletTransactionsFragment.getInstance(idOfBill!!, viewModel.bills.value!!)
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.flWallet, walletTransactionsFragment)
                transaction.addToBackStack("")
                transaction.commit()
            }
        })
    }

    fun observeScreenChange() {
        viewModel.selectedScreen.observe(this, object : Observer<Screen> {
            override fun onChanged(newScreen: Screen?) {
                when (newScreen) {
                    Screen.MY_WALLET -> loadMyWalletFragment()
                    Screen.DEPOSIT -> {}
                    Screen.EXCHANGE -> {}
                    Screen.SEND -> {}
                    Screen.MENU -> {}
                }
            }
        })
    }

    private fun loadMyWalletFragment() {
        val walletFragment = MyWalletFragment.getInstance(viewModel.bills.value!!)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flWallet, walletFragment)
        transaction.addToBackStack("")
        transaction.commit()
    }



}
