package com.storiqa.storiqawallet.screen_main.my_wallet

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
import com.storiqa.storiqawallet.constants.Extras
import com.storiqa.storiqawallet.databinding.FragmentMywalletBinding
import com.storiqa.storiqawallet.db.PreferencesHelper
import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.objects.BillClicked
import kotlinx.android.synthetic.main.fragment_mywallet.*
import org.greenrobot.eventbus.EventBus


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

        refreshBillInfo()
        PreferencesHelper(context!!).setQuickLaunchFinished()
    }

    private fun refreshBillInfo() {
        rvBills?.apply {
            adapter = BillsAdapter(arguments?.getSerializable(Extras().bill) as Array<Bill>) { positionOfClickedBill ->
                EventBus.getDefault().post(BillClicked(positionOfClickedBill))
            }
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    companion object {
        fun getInstance(bills : Array<Bill>) : MyWalletFragment {
            val myWalletFragment = MyWalletFragment()
            val bundle = Bundle()
            bundle.putSerializable(Extras().bill, bills)
            myWalletFragment.arguments = bundle
            return myWalletFragment
        }
    }
}