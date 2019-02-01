package com.storiqa.storiqawallet.ui.main.transactions

import android.os.Bundle
import android.view.View
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentTransactionsBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.base.IBaseActivity

class TransactionsFragment : BaseFragment<FragmentTransactionsBinding, TransactionsViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_transactions

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<TransactionsViewModel> = TransactionsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        (activity as IBaseActivity).setupActionBar(binding.toolbar, backButtonEnabled = true)
    }
}