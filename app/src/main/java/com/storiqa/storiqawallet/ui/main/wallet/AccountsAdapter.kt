package com.storiqa.storiqawallet.ui.main.wallet

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.storiqa.storiqawallet.data.model.Card
import com.storiqa.storiqawallet.databinding.ItemAccountBinding

class AccountsAdapter(private var cards: List<Card>) :
        RecyclerView.Adapter<AccountsAdapter.ViewHolder>() {

    private val currencyFiat = "USD"

    fun updateAccounts(newCards: List<Card>) {
        cards = newCards
        notifyDataSetChanged()
    }

    private var layoutInflater: LayoutInflater? = null

    private fun getInflater(context: Context): LayoutInflater {
        return layoutInflater ?: LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(ItemAccountBinding.inflate(getInflater(parent.context), parent, false))

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    class ViewHolder(private val binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card) {

            binding.apply {
                this.card = card
                executePendingBindings()
                //root.setOnClickListener { _ -> onClick(layoutPosition) }

                //val billInfo = BillInfo(bill)
                //billInfo.initBillView(root)
            }
        }
    }

}