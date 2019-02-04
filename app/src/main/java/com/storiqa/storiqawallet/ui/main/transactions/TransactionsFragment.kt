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

    private var isListsInitialized = false

    private var transactionsAdapter: TransactionsAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_transactions

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<TransactionsViewModel> = TransactionsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        subscribeEvents()

        val address = arguments?.getString(KEY_ADDRESS)!!
        viewModel.loadTransactions(address)
    }

    private fun initView() {
        (activity as IBaseActivity).setupActionBar(binding.toolbar, backButtonEnabled = true)

        binding.tabLayout.setup()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                (activity as IBaseActivity).hideKeyboard()
                viewModel.updateTransactions(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                (activity as IBaseActivity).hideKeyboard()
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                (activity as IBaseActivity).hideKeyboard()
            }
        })
    }

    private fun subscribeEvents() {
        viewModel.updateTransactions.observe(this, Observer {
            updateTransactions(it)
        })
    }

    private fun updateTransactions(transactions: List<Transaction>) {
        if (!isListsInitialized) {
            initTransactionsRecycler(transactions)
        } else {
            transactionsAdapter?.updateAccounts(transactions)
        }
    }

    private fun initTransactionsRecycler(transactions: List<Transaction>) {
        transactionsAdapter = TransactionsAdapter(transactions)
        binding.transactionsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionsAdapter
        }
    }
}