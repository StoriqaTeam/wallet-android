package com.storiqa.storiqawallet.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ItemTransactionBinding
import com.storiqa.storiqawallet.enums.TransactionType
import com.storiqa.storiqawallet.objects.Transaction
import kotlinx.android.synthetic.main.item_transaction.view.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk27.coroutines.onClick

class TransactionAdapter(val transactions: Array<Transaction>, val onClick: () -> Unit) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    private fun getInflater(context: Context): LayoutInflater {
        return layoutInflater ?: LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder = ViewHolder(ItemTransactionBinding.inflate(getInflater(parent.context), parent, false))

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactions[position], onClick)
    }


    class ViewHolder(val binder: ItemTransactionBinding) : RecyclerView.ViewHolder(binder.root) {
        fun bind(transaction: Transaction, onClick: () -> Unit) {
            binder.apply {
                this.transaction = transaction
                root.onClick { onClick() }
                executePendingBindings()

                var sign = ""
                var transactionTypeLocalicedText = ""
                var transactionDescriptionLocaliced = ""

                when (transaction.transactionType) {
                    TransactionType.SEND -> {
                        sign = "-"
                        transactionTypeLocalicedText = root.context.getString(R.string.sent)
                        transactionDescriptionLocaliced = "${root.context.getString(R.string.to)} ${transaction.transactionReceiverName}"
                    }

                    TransactionType.RECEIVE -> {
                        sign = "+"
                        transactionTypeLocalicedText = root.context.getString(R.string.recieve)
                        transactionDescriptionLocaliced = "${root.context.getString(R.string.from)} ${transaction.transactionSenderWallet}"
                    }
                }

                root.tvTransactionHeader.text = "$transactionTypeLocalicedText ${transaction.tokenType}"
                root.tvTransactionDescription.text = transactionDescriptionLocaliced
                root.tvAmountInToken.text = "$sign${transaction.amountInToken} ${transaction.tokenType}"
                root.tvAmountInDollars.text = "$sign\$${transaction.amountInDollars}"
                root.setPadding(root.context.dip(20), 0, root.context.dip(20), 0)

            }
        }
    }
}