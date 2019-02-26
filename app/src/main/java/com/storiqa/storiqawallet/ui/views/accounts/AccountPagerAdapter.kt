package com.storiqa.storiqawallet.ui.views.accounts

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.databinding.ItemAccountLargeBinding
import com.storiqa.storiqawallet.databinding.ItemAccountMediumBinding
import com.storiqa.storiqawallet.databinding.ItemAccountSmallBinding
import com.storiqa.storiqawallet.ui.common.TopAlignSuperscriptSpan

class AccountPagerAdapter(
        private val context: Context,
        private var accounts: List<Account>,
        private val size: AccountCardSize
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = when (size) {
            AccountCardSize.LARGE -> {
                val binding: ItemAccountLargeBinding = DataBindingUtil.inflate(inflater, R.layout.item_account_large, container, false)
                binding.setVariable(BR.account, accounts[position])
                binding.executePendingBindings()

                val spannable = SpannableString(accounts[position].currency.currencyISO + " " + accounts[position].balanceFormatted)
                spannable.setSpan(TopAlignSuperscriptSpan(0.35f), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.tvBalance.text = spannable

                binding.root
            }
            AccountCardSize.MEDIUM -> {
                val binding: ItemAccountMediumBinding = DataBindingUtil.inflate(inflater, R.layout.item_account_medium, container, false)
                binding.setVariable(BR.account, accounts[position])
                binding.executePendingBindings()

                val spannable = SpannableString(accounts[position].currency.currencyISO + " " + accounts[position].balanceFormatted)
                spannable.setSpan(TopAlignSuperscriptSpan(0.35f), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.tvBalance.text = spannable

                binding.root
            }
            AccountCardSize.SMALL -> {
                val binding: ItemAccountSmallBinding = DataBindingUtil.inflate(inflater, R.layout.item_account_small, container, false)
                binding.setVariable(BR.account, accounts[position])
                binding.executePendingBindings()

                val spannable = SpannableString(accounts[position].currency.currencyISO + " " + accounts[position].balanceFormatted)
                spannable.setSpan(TopAlignSuperscriptSpan(0.35f), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.tvBalance.text = spannable

                binding.root
            }
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return accounts.size
    }

    override fun getItemPosition(obj: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    fun updateAccounts(newCards: List<Account>) {
        accounts = newCards
        notifyDataSetChanged()
    }
}