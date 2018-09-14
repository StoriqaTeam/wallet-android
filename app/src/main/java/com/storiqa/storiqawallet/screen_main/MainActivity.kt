package com.storiqa.storiqawallet.screen_main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.adapters.BillsAdapter
import com.storiqa.storiqawallet.databinding.ActivityMainBinding
import com.storiqa.storiqawallet.db.PreferencesHelper
import com.storiqa.storiqawallet.objects.Bill
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel.refreshBillInfo()
        PreferencesHelper(this).setQuickLaunchFinished()
        setBillObservable()

    }

    fun setBillObservable() {
        viewModel.bills.observe(this, Observer<List<Bill>> { newBills ->
            rvBills.apply {
                adapter = BillsAdapter(newBills!!, { })
                layoutManager = LinearLayoutManager(this@MainActivity)
                setHasFixedSize(true)
            }
        })
    }
}
