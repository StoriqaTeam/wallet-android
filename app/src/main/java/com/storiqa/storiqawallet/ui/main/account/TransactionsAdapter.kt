package com.storiqa.storiqawallet.ui.main.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.data.model.Transaction
import com.storiqa.storiqawallet.databinding.ItemTransactionBinding

class TransactionsAdapter(private var transactions: List<Transaction>, val onClick: ((position: Int, element: View, transaction: String) -> Unit)? = null) :
        RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {

    fun updateAccounts(newTransactions: List<Transaction>) {
        transactions = newTransactions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    inner class ViewHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction) {
            binding.apply {
                this.transaction = transaction
                executePendingBindings()
                val onClickListener = onClick
                if (onClickListener != null)
                    root.setOnClickListener {
                        onClickListener(layoutPosition, root, App.res.getString(R.string.transition_account))
                    }
            }
        }
    }

}