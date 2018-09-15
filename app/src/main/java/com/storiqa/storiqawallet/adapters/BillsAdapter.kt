package com.storiqa.storiqawallet.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.storiqa.storiqawallet.databinding.ItemCardBinding
import com.storiqa.storiqawallet.objects.Bill
import org.jetbrains.anko.dip

class BillsAdapter(private val bills : Array<Bill>, val onClick : (position : Int) -> Unit) : RecyclerView.Adapter<BillsAdapter.ViewHolder>() {

    private var layoutInflater : LayoutInflater? = null

    private fun getInflater(context : Context) : LayoutInflater {
        return layoutInflater?: LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = ViewHolder(ItemCardBinding.inflate(getInflater(parent.context), parent, false))

    override fun getItemCount(): Int = bills.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bills[position], onClick)
    }

    class ViewHolder(private val binding : ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bill: Bill, onClick : (position : Int) -> Unit) {
            binding.bill = bill
            binding.executePendingBindings()
            binding.root.setOnClickListener { _ -> onClick(layoutPosition) }
            binding.root.setPadding(0,0,0, binding.root.context.dip(17))
        }

    }
}