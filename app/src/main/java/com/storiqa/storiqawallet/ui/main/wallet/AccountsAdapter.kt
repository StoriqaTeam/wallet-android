package com.storiqa.storiqawallet.ui.main.wallet

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.storiqa.storiqawallet.databinding.ItemBillDeprecatedBinding
import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.objects.BillInfo

class AccountsAdapter(private val bills: ArrayList<Bill>, val onClick: (position: Int) -> Unit) : RecyclerView.Adapter<AccountsAdapter.ViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    private fun getInflater(context: Context): LayoutInflater {
        return layoutInflater ?: LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(ItemBillDeprecatedBinding.inflate(getInflater(parent.context), parent, false))

    override fun getItemCount(): Int = bills.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bills[position], onClick)
    }

    class ViewHolder(private val binding: ItemBillDeprecatedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bill: Bill, onClick: (position: Int) -> Unit) {

            binding.apply {
                this.account = bill
                executePendingBindings()
                root.setOnClickListener { _ -> onClick(layoutPosition) }
                //root.setPadding(root.context.dip(17), 0, root.context.dip(17), root.context.dip(17))

                if (layoutPosition == 0) {
                    //root.setPadding(root.context.dip(17), root.context.dip(17), root.context.dip(17), root.context.dip(17))
                }

                val billInfo = BillInfo(bill)
                billInfo.initBillView(root)
            }
        }
    }

}