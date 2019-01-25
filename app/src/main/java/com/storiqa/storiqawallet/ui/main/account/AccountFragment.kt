package com.storiqa.storiqawallet.ui.main.account

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentAccountBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.base.IBaseActivity
import com.storiqa.storiqawallet.ui.main.wallet.AccountsAdapter

class AccountFragment : BaseFragment<FragmentAccountBinding, AccountViewModel>() {

    private lateinit var adapter: AccountsAdapter

    private var position = -1

    override fun getLayoutId(): Int = R.layout.fragment_account

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<AccountViewModel> = AccountViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view.context)

        viewModel.updateAccounts.observe(this, Observer {
            adapter.updateAccounts(viewModel.cards)
            if (position != -1) {
                binding.accountsRecycler.scrollToPosition(position)
                position = -1
            }
        })

        position = arguments?.getInt("POSITION") ?: -1
    }

    private fun initView(context: Context) {
        (activity as IBaseActivity).setupActionBar(binding.toolbar, " ", true)

        adapter = AccountsAdapter(viewModel.cards)

        binding.accountsRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.accountsRecycler.adapter = adapter

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.accountsRecycler)
    }
}