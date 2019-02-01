package com.storiqa.storiqawallet.ui.main.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.data.model.Transaction
import com.storiqa.storiqawallet.databinding.FragmentAccountBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.base.IBaseActivity
import com.storiqa.storiqawallet.utils.convertDpToPx

class AccountFragment : BaseFragment<FragmentAccountBinding, AccountViewModel>() {

    private var accountsAdapter: AccountPagerAdapter? = null
    private var transactionsAdapter: TransactionsAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_account

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<AccountViewModel> = AccountViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentPosition = arguments?.getInt("POSITION") ?: 0

        initView()

        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModel.updateAccounts.observe(this, Observer {
            updateAccounts(it)
        })

        viewModel.updateTransactions.observe(this, Observer {
            updateTransactions(it)
        })
    }

    private fun initView() {
        (activity as IBaseActivity).setupActionBar(binding.toolbar, " ", true)
    }

    private fun initAccountsPager(accounts: ArrayList<Account>) {
        accountsAdapter = AccountPagerAdapter(this@AccountFragment, accounts)
        binding.accountsPager.apply {
            adapter = accountsAdapter
            setPadding(convertDpToPx(30f).toInt(), 0, convertDpToPx(30f).toInt(), 0)
            clipToPadding = false
            pageMargin = convertDpToPx(20f).toInt()
            setCurrentItem(viewModel.currentPosition, false)
            addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    binding.toolbar.title = accounts[position].name
                    viewModel.onAccountSelected(position)
                }
            })
        }
        viewModel.onAccountSelected(viewModel.currentPosition)
    }

    private fun initTransactionsRecycler(transactions: List<Transaction>) {
        transactionsAdapter = TransactionsAdapter(transactions)
        binding.transactionsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionsAdapter
        }
    }

    private fun updateAccounts(accounts: ArrayList<Account>) {
        if (accountsAdapter == null) {
            initAccountsPager(accounts)
            binding.toolbar.title = accounts[viewModel.currentPosition].name
        } else
            accountsAdapter?.updateAccounts(accounts)


    }

    private fun updateTransactions(transactions: List<Transaction>) {
        if (transactionsAdapter == null) {
            initTransactionsRecycler(transactions)
        } else
            transactionsAdapter?.updateAccounts(transactions)
    }
}