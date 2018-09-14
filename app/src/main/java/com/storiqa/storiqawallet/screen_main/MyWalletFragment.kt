package com.storiqa.storiqawallet.screen_main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.adapters.BillsAdapter
import com.storiqa.storiqawallet.databinding.FragmentMywalletBinding
import com.storiqa.storiqawallet.db.PreferencesHelper
import com.storiqa.storiqawallet.objects.Bill
import kotlinx.android.synthetic.main.fragment_mywallet.*


class MyWalletFragment : Fragment() {

    lateinit var viewModel: MyWalletViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(MyWalletViewModel::class.java)

        val binding : FragmentMywalletBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mywallet, container, false)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.refreshBillInfo()
        PreferencesHelper(context!!).setQuickLaunchFinished()
        setBillObservable()
    }

    fun setBillObservable() {
        viewModel.bills.observe(this, Observer<List<Bill>> { newBills ->
            rvBills.apply {
                adapter = BillsAdapter(newBills!!, { })
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        })
    }
}