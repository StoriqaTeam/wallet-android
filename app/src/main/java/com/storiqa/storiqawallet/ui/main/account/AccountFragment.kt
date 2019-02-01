package com.storiqa.storiqawallet.ui.main.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentAccountBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.base.IBaseActivity
import com.storiqa.storiqawallet.utils.convertDpToPx

class AccountFragment : BaseFragment<FragmentAccountBinding, AccountViewModel>() {

    private lateinit var accountsAdapter: AccountPagerAdapter
    private lateinit var transactionsAdapter: TransactionsAdapter

    override fun getLayoutId(): Int = R.layout.fragment_account

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<AccountViewModel> = AccountViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isAccountsInitialized = false
        viewModel.currentPosition = arguments?.getInt("POSITION") ?: 0

        initView()

        viewModel.updateAccounts.observe(this, Observer {
            accountsAdapter.updateAccounts(viewModel.cards)
            if (!viewModel.isAccountsInitialized) {
                binding.toolbar.title = viewModel.cards[viewModel.currentPosition].name
                binding.accountsPager.setCurrentItem(viewModel.currentPosition, false)
                viewModel.isAccountsInitialized = true
            }
        })

        viewModel.updateTransactions.observe(this, Observer {
            if (!viewModel.isTransactionsInitialized) {
                transactionsAdapter = TransactionsAdapter(it)
                binding.transactionsRecycler.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = transactionsAdapter
                }
            } else
                transactionsAdapter.updateAccounts(it)
        })
    }

    private fun initView() {
        (activity as IBaseActivity).setupActionBar(binding.toolbar, " ", true)

        accountsAdapter = AccountPagerAdapter(this@AccountFragment, viewModel.cards)
        binding.accountsPager.apply {
            adapter = accountsAdapter
            setPadding(convertDpToPx(30f).toInt(), 0, convertDpToPx(30f).toInt(), 0)
            clipToPadding = false
            pageMargin = convertDpToPx(20f).toInt()
            addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    binding.toolbar.title = viewModel.cards[position].name
                    viewModel.onAccountSelected(position)
                }
            })
        }
    }
}