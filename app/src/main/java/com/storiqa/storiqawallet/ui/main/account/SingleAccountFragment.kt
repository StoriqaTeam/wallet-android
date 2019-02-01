package com.storiqa.storiqawallet.ui.main.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ItemAccountBinding

class SingleAccountFragment : Fragment() {

    companion object {
        const val KEY_CARD = "key_card"
    }

    private lateinit var binding: ItemAccountBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val account = arguments?.get(KEY_CARD)

        binding = DataBindingUtil.inflate(inflater, R.layout.item_account, container, false)
        binding.setVariable(BR.account, account)
        binding.executePendingBindings()

        return binding.root
    }

}