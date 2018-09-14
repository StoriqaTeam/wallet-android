package com.storiqa.storiqawallet.screen_main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.storiqa.storiqawallet.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val walletFragment = MyWalletFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.flWallet, walletFragment)
        transaction.commit()
    }


}
