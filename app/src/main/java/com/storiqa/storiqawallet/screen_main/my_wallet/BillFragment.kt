package com.storiqa.storiqawallet.screen_main.my_wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.storiqa.storiqawallet.constants.Extras
import com.storiqa.storiqawallet.databinding.ItemBillDeprecatedBinding
import com.storiqa.storiqawallet.databinding.ItemBillSmallBinding
import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.objects.BillInfo

class BillFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, arguments?.getInt(Extras().billRes)!!, container, false)
        val bill = arguments?.getSerializable(Extras().bill) as Bill

        binding.apply {
            var isSmall = false
            if (binding is ItemBillDeprecatedBinding) {
                isSmall = false
                (binding as ItemBillDeprecatedBinding).account = bill
            } else {
                isSmall = true
                (binding as ItemBillSmallBinding).bill = bill
            } //TODO refactore. Break of Open/close principle

            executePendingBindings()
            //root.setPadding(dip(7), 0, dip(7), 0)

            val billInfo = BillInfo(bill)
            billInfo.initBillView(root, isSmall)
        }
        return binding.root
    }

    companion object {
        fun getInstance(resId: Int, bill: Bill): BillFragment {
            val bundle = Bundle()
            val billFragment = BillFragment()
            bundle.putSerializable(Extras().bill, bill)
            bundle.putSerializable(Extras().billRes, resId)
            billFragment.arguments = bundle
            return billFragment
        }
    }
}