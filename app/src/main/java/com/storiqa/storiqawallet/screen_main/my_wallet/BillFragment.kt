package com.storiqa.storiqawallet.screen_main.my_wallet

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.constants.Extras
import com.storiqa.storiqawallet.databinding.ItemCardBinding
import com.storiqa.storiqawallet.objects.Bill
import org.jetbrains.anko.support.v4.dip

class BillFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding : ItemCardBinding = DataBindingUtil.inflate(inflater, R.layout.item_card, container, false)
        binding.bill = arguments?.getSerializable(Extras().bill) as Bill
        binding.executePendingBindings()
        binding.root.setPadding(dip(5),0, dip(5) ,0)
        return binding.root
    }

    companion object {
        fun getInstance(bill : Bill) : BillFragment {
            val bundle = Bundle()
            val billFragment = BillFragment()
            bundle.putSerializable(Extras().bill, bill)
            billFragment.arguments = bundle
            return billFragment
        }
    }
}