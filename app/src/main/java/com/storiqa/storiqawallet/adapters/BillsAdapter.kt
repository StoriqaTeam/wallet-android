package com.storiqa.storiqawallet.adapters

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.storiqa.storiqawallet.databinding.ItemBillBinding
import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.objects.BillInfo
import kotlinx.android.synthetic.main.fragment_send.view.*
import kotlinx.android.synthetic.main.item_bill.view.*
import org.jetbrains.anko.dip

class BillsAdapter(private val bills : Array<Bill>, val onClick : (position : Int) -> Unit) : RecyclerView.Adapter<BillsAdapter.ViewHolder>() {

    private var layoutInflater : LayoutInflater? = null

    private fun getInflater(context : Context) : LayoutInflater {
        return layoutInflater?: LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = ViewHolder(ItemBillBinding.inflate(getInflater(parent.context), parent, false))

    override fun getItemCount(): Int = bills.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bills[position], onClick)
    }

    class ViewHolder(private val binding : ItemBillBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bill: Bill, onClick : (position : Int) -> Unit) {

            binding.apply {
                this.bill = bill
                executePendingBindings()
                root.setOnClickListener { _ -> onClick(layoutPosition) }
                root.setPadding(root.context.dip(20),0,root.context.dip(20), root.context.dip(17))

                val billInfo = BillInfo(bill)
                root.tvBillStatus.text = root.context.getString(billInfo.getBillStatus())
                root.ivBillImage.setBackgroundResource(billInfo.getBillImage())
                root.clBill.setBackgroundResource(billInfo.getBillColor())

                val textColor = ResourcesCompat.getColor(root.context.resources, billInfo.getBillTextColor(), null)
                root.tvTokenType.setTextColor(textColor)
                root.tvHolderName.setTextColor(textColor)
                root.tvAmount.setTextColor(textColor)
                root.ivBillLogo.setImageResource(billInfo.getBillImage())

                val additionalInforColor = ResourcesCompat.getColor(root.context.resources, billInfo.getBillInfoColors(), null)
                root.tvBillStatus.setTextColor(additionalInforColor)
                root.tvAmountInDollars.setTextColor(additionalInforColor)
                root.tvHolderNameLabel.setTextColor(additionalInforColor)
            }
        }
    }
}