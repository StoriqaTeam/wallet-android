package com.storiqa.storiqawallet.ui.main.exchange

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.databinding.FragmentExchangeBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.base.IBaseActivity
import com.storiqa.storiqawallet.ui.main.account.AccountCardSize
import com.storiqa.storiqawallet.ui.main.account.AccountPagerAdapter
import com.storiqa.storiqawallet.utils.convertDpToPx

class ExchangeFragment : BaseFragment<FragmentExchangeBinding, ExchangeViewModel>() {

    private var accountsAdapter: AccountPagerAdapter? = null

    private var isRestoring = false

    override fun getLayoutId(): Int = R.layout.fragment_exchange

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<ExchangeViewModel> = ExchangeViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        subscribeEvents()
    }

    private fun initView() {
        (activity as IBaseActivity).setupActionBar(binding.toolbar)

        if (isRestoring) {
            initAccountsPager(viewModel.accounts)
            isRestoring = false
        }

        binding.accountsToPager.init(this, 1)
    }

    private fun subscribeEvents() {
        viewModel.updateAccounts.observe(this, Observer {
            updateAccounts(it)
            binding.accountsToPager.accounts = it
        })
    }

    private fun initAccountsPager(accounts: List<Account>) {
        accountsAdapter = AccountPagerAdapter(this@ExchangeFragment, accounts, AccountCardSize.SMALL)
        binding.accountsFromPager.apply {
            adapter = accountsAdapter
            setPadding(convertDpToPx(30f).toInt(), 0, convertDpToPx(30f).toInt(), 0)
            clipToPadding = false
            pageMargin = convertDpToPx(20f).toInt()
            setCurrentItem(1, false)
            addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    viewModel.onAccountSelected(position)
                }
            })
        }
        viewModel.onAccountSelected(viewModel.currentPosition)
    }

    private fun updateAccounts(accounts: List<Account>) {
        if (accountsAdapter == null) {
            initAccountsPager(accounts)
        } else
            accountsAdapter?.updateAccounts(accounts)
    }

}