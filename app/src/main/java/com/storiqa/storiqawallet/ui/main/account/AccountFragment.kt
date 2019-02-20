package com.storiqa.storiqawallet.ui.main.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.data.model.Transaction
import com.storiqa.storiqawallet.databinding.FragmentAccountBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.base.IBaseActivity

class AccountFragment : BaseFragment<FragmentAccountBinding, AccountViewModel>() {

    companion object {
        const val KEY_POSITION = "key_position"
    }

    private var accountsAdapter: AccountPagerAdapter? = null
    private var transactionsAdapter: TransactionsAdapter? = null

    private var isRestoring = false
    private var isScrollNeeded = false

    override fun getLayoutId(): Int = R.layout.fragment_account

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<AccountViewModel> = AccountViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isRestoring)
            viewModel.currentPosition = arguments?.getInt(KEY_POSITION) ?: 0

        initView()

        subscribeEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isRestoring = true
    }

    private fun initView() {
        (activity as IBaseActivity).setupActionBar(binding.toolbar, " ", true)

        binding.accountChooser.init(requireContext(), viewModel.currentPosition)
        binding.accountChooser.setOnPageSelectedListener { position, title ->
            if (position != viewModel.currentPosition)
                isScrollNeeded = true
            binding.toolbar.title = title
            viewModel.onAccountSelected(position)
        }
        if (isRestoring) {
            updateAccounts(viewModel.accounts)
            initTransactionsRecycler(viewModel.transactions)
            isRestoring = false
        }
    }

    private fun subscribeEvents() {
        viewModel.updateAccounts.observe(this, Observer {
            updateAccounts(it)
        })

        viewModel.updateTransactions.observe(this, Observer {
            updateTransactions(it)
        })
    }

    private fun initTransactionsRecycler(transactions: List<Transaction>) {
        transactionsAdapter = TransactionsAdapter(transactions, viewModel::onTransactionClicked)

        binding.transactionsRecycler.adapter = transactionsAdapter
        binding.transactionsRecycler.layoutManager = LinearLayoutManager(context)
    }

    private fun updateAccounts(accounts: List<Account>) {
        binding.accountChooser.accounts = accounts
    }

    private fun updateTransactions(transactions: List<Transaction>) {
        if (transactionsAdapter == null) {
            initTransactionsRecycler(transactions)
        } else {
            transactionsAdapter?.updateAccounts(transactions)
            if (isScrollNeeded) {
                binding.transactionsRecycler.scrollToPosition(0)
                isScrollNeeded = false
            }
        }
    }
}