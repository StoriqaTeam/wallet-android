package com.storiqa.storiqawallet.ui.main.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.databinding.ItemAccountSmallBinding

class SmallAccountFragment : Fragment() {

    companion object {
        private const val KEY_ACCOUNT = "key_account"

        fun newInstance(account: Account): SmallAccountFragment {
            val bundle = Bundle()
            bundle.putParcelable(KEY_ACCOUNT, account)
            return SmallAccountFragment().also { it.arguments = bundle }
        }
    }

    private lateinit var binding: ItemAccountSmallBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val account: Account = arguments?.get(KEY_ACCOUNT) as Account

        binding = DataBindingUtil.inflate(inflater, R.layout.item_account_small, container, false)
        binding.setVariable(BR.account, account)
        binding.executePendingBindings()
        return binding.root
    }

}