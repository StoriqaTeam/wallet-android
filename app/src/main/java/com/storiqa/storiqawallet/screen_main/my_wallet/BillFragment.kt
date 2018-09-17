package com.storiqa.storiqawallet.screen_main.my_wallet

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.constants.Extras
import com.storiqa.storiqawallet.databinding.ItemBillBinding
import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.objects.BillInfo
import kotlinx.android.synthetic.main.item_bill.view.*
import org.jetbrains.anko.support.v4.dip

class BillFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding : ItemBillBinding = DataBindingUtil.inflate(inflater, R.layout.item_bill, container, false)
        val bill = arguments?.getSerializable(Extras().bill) as Bill

        binding.apply {
            this.bill = bill
            executePendingBindings()
            root.setPadding(dip(5), 0, dip(5), 0)

            val billInfo = BillInfo(bill)
            root.tvBillStatus.text = root.context.getString(billInfo.getBillStatus())
            root.ivBillImage.setBackgroundResource(billInfo.getBillImage())
            root.clBill.setBackgroundResource(billInfo.getBillColor())

            val textColor = ResourcesCompat.getColor(root.context.resources, billInfo.getBillTextColor(), null)
            root.tvTokenType.setTextColor(textColor)
            root.tvHolderName.setTextColor(textColor)
            root.tvAmount.setTextColor(textColor)
            //TODO refactor (duplicate logic in adapter)
        }
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