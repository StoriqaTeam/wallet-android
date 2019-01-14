package com.storiqa.storiqawallet.screen_main.my_wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.adapters.BillsAdapter
import com.storiqa.storiqawallet.constants.Extras
import com.storiqa.storiqawallet.databinding.FragmentMenuDeprecatedBinding
import com.storiqa.storiqawallet.db.PreferencesHelper
import com.storiqa.storiqawallet.enums.Screen
import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.screen_main.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_mywallet_deprecated.*


class MyWalletFragment : androidx.fragment.app.Fragment() {

    lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
        viewModel.selectedScreen.set(Screen.MY_WALLET)

        val binding: FragmentMenuDeprecatedBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mywallet_deprecated, container, false)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        refreshBillInfo()
        PreferencesHelper(context!!).setQuickLaunchFinished(true)
    }

    private fun refreshBillInfo() {
        rvBills?.apply {
            adapter = BillsAdapter(viewModel.bills.value!!) { positionOfClickedBill ->
                viewModel.loadBillInfo(viewModel.bills.value!![positionOfClickedBill].id)
            }
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    companion object {
        fun getInstance(bills: Array<Bill>): MyWalletFragment {
            val myWalletFragment = MyWalletFragment()
            val bundle = Bundle()
            bundle.putSerializable(Extras().bill, bills)
            myWalletFragment.arguments = bundle
            return myWalletFragment
        }
    }
}