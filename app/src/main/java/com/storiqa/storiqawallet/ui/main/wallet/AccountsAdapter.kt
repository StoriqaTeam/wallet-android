package com.storiqa.storiqawallet.ui.main.wallet

import android.text.SpannableString
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.databinding.ItemAccountLargeBinding
import com.storiqa.storiqawallet.ui.common.TopAlignSuperscriptSpan


class AccountsAdapter(private var cards: List<Account>, val onClick: ((position: Int, element: View, transaction: String) -> Unit)? = null) :
        RecyclerView.Adapter<AccountsAdapter.ViewHolder>() {

    fun updateAccounts(newCards: List<Account>) {
        cards = newCards
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(ItemAccountLargeBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    inner class ViewHolder(private val binding: ItemAccountLargeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(account: Account) {
            binding.apply {
                this.account = account
                executePendingBindings()
                val onClickListener = onClick
                if (onClickListener != null)
                    root.setOnClickListener {
                        onClickListener(layoutPosition, root, App.res.getString(R.string.transition_account))
                    }
            }
            val spannable = SpannableString(account.currency.currencyISO + " " + account.balanceFormatted)
            spannable.setSpan(TopAlignSuperscriptSpan(0.35f), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvBalance.text = spannable
        }
    }

}