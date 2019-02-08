package com.storiqa.storiqawallet.ui.main.account

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.databinding.ItemAccountBinding
import com.storiqa.storiqawallet.ui.common.TopAlignSuperscriptSpan

class SingleAccountFragment : Fragment() {

    companion object {
        const val KEY_CARD = "key_card"
    }

    private lateinit var binding: ItemAccountBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val account: Account = arguments?.get(KEY_CARD) as Account

        binding = DataBindingUtil.inflate(inflater, R.layout.item_account, container, false)
        binding.setVariable(BR.account, account)
        binding.executePendingBindings()

        val spannable = SpannableString(account.currency + " " + account.balanceFormatted)
        spannable.setSpan(TopAlignSuperscriptSpan(0.35f), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvBalance.text = spannable

        return binding.root
    }

}