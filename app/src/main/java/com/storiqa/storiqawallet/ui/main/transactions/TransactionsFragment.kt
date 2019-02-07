package com.storiqa.storiqawallet.ui.main.transactions

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.data.model.Transaction
import com.storiqa.storiqawallet.databinding.FragmentTransactionsBinding
import com.storiqa.storiqawallet.extensions.setup
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.base.IBaseActivity
import com.storiqa.storiqawallet.ui.main.account.TransactionsAdapter

class TransactionsFragment : BaseFragment<FragmentTransactionsBinding, TransactionsViewModel>() {

    companion object {
        const val KEY_ADDRESS = "key_address"
    }

    private var isRestoring = false
    private var isScrollNeeded = false

    private var transactionsAdapter: TransactionsAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_transactions

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<TransactionsViewModel> = TransactionsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        subscribeEvents()

        if (isRestoring) {
            initTransactionsRecycler(viewModel.getTransactions())
        } else {
            val address = arguments?.getString(KEY_ADDRESS)!!
            viewModel.loadTransactions(address)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isRestoring = true
    }

    private fun initView() {
        (activity as IBaseActivity).setupActionBar(binding.toolbar, backButtonEnabled = true)

        binding.tabLayout.setup()
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                (activity as IBaseActivity).hideKeyboard()
                if (tab.position != viewModel.currentTabPosition)
                    isScrollNeeded = true
                viewModel.updateTransactions(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                (activity as IBaseActivity).hideKeyboard()
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                (activity as IBaseActivity).hideKeyboard()
            }
        })
        binding.tabLayout.getTabAt(viewModel.currentTabPosition)?.select()
    }

    private fun subscribeEvents() {
        viewModel.updateTransactions.observe(this, Observer {
            updateTransactions(it)
        })
    }

    private fun updateTransactions(transactions: List<Transaction>) {
        if (transactionsAdapter == null)
            initTransactionsRecycler(transactions)
        else {
            transactionsAdapter?.updateAccounts(transactions)
            if (isScrollNeeded) {
                binding.transactionsRecycler.scrollToPosition(0)
                binding.transactionsRecycler.stopNestedScroll()
                isScrollNeeded = false
            }
        }
    }

    private fun initTransactionsRecycler(transactions: List<Transaction>) {
        transactionsAdapter = TransactionsAdapter(transactions, viewModel::onTransactionClicked)
        binding.transactionsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionsAdapter
        }
    }
}