package com.storiqa.storiqawallet.ui.main.wallet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.data.model.Card
import com.storiqa.storiqawallet.databinding.ItemAccountBinding

class AccountsAdapter(private var cards: List<Card>, val onClick: ((position: Int, element: View, transaction: String) -> Unit)? = null) :
        RecyclerView.Adapter<AccountsAdapter.ViewHolder>() {

    fun updateAccounts(newCards: List<Card>) {
        cards = newCards
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    inner class ViewHolder(private val binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Card) {
            binding.apply {
                this.card = card
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