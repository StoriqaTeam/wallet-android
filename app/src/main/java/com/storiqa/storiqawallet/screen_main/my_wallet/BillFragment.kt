package com.storiqa.storiqawallet.screen_main.my_wallet

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.constants.Extras
import com.storiqa.storiqawallet.databinding.ItemBillBinding
import com.storiqa.storiqawallet.databinding.ItemBillSmallBinding
import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.objects.BillInfo
import kotlinx.android.synthetic.main.item_bill.view.*
import org.jetbrains.anko.support.v4.dip

class BillFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding : ViewDataBinding = DataBindingUtil.inflate(inflater, arguments?.getInt(Extras().billRes)!! , container, false)
        val bill = arguments?.getSerializable(Extras().bill) as Bill

        binding.apply {
            if(binding is ItemBillBinding) {
                (binding as ItemBillBinding).bill = bill
            } else {
                (binding as ItemBillSmallBinding).bill = bill
            } //TODO refactore. Break of Open/close principle

            executePendingBindings()
            root.setPadding(dip(5), 0, dip(5), 0)

            val billInfo = BillInfo(bill)
            billInfo.initBillView(root)
        }
        return binding.root
    }

    companion object {
        fun getInstance(resId : Int, bill : Bill) : BillFragment {
            val bundle = Bundle()
            val billFragment = BillFragment()
            bundle.putSerializable(Extras().bill, bill)
            bundle.putSerializable(Extras().billRes, resId)
            billFragment.arguments = bundle
            return billFragment
        }
    }
}