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

class LargeAccountFragment : Fragment() {

    companion object {
        private const val KEY_ACCOUNT = "key_account"

        fun newInstance(account: Account): LargeAccountFragment {
            val bundle = Bundle()
            bundle.putParcelable(KEY_ACCOUNT, account)
            return LargeAccountFragment().also { it.arguments = bundle }
        }
    }

    private lateinit var binding: ItemAccountBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val account: Account = arguments?.get(KEY_ACCOUNT) as Account

        binding = DataBindingUtil.inflate(inflater, R.layout.item_account_large, container, false)
        binding.setVariable(BR.account, account)
        binding.executePendingBindings()

        val spannable = SpannableString(account.currency + " " + account.balanceFormatted)
        spannable.setSpan(TopAlignSuperscriptSpan(0.35f), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvBalance.text = spannable

        return binding.root
    }

}