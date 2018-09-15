package com.storiqa.storiqawallet.screen_main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivityMainBinding
import com.storiqa.storiqawallet.enums.Screen

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel.openMyWalletScreen()

        setScreenChangeObservable()
    }

    fun setScreenChangeObservable() {
        viewModel.selectedScreen.observe(this, object : Observer<Screen> {
            override fun onChanged(t: Screen?) {
                when (t) {
                    Screen.MY_WALLET -> loadMyWalletFragment()

                    else -> {
                        Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun loadMyWalletFragment() {
        val walletFragment = MyWalletFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.flWallet, walletFragment)
        transaction.commit()
    }

}
