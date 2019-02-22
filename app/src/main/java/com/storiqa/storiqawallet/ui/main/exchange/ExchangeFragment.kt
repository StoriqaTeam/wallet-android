package com.storiqa.storiqawallet.ui.main.exchange

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.databinding.FragmentExchangeBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.base.IBaseActivity
import com.storiqa.storiqawallet.ui.common.addUserInputListener

class ExchangeFragment : BaseFragment<FragmentExchangeBinding, ExchangeViewModel>() {

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
            updateAccounts(viewModel.accounts)
            isRestoring = false
        }

        binding.accountFromChooser.init(requireContext(), 0)
        binding.accountFromChooser.setOnPageSelectedListener { position, _ ->
            viewModel.onAccountFromSelected(position)
        }

        binding.accountToChooser.init(requireContext(), 0)
        binding.accountToChooser.setOnPageSelectedListener { position, _ ->
            viewModel.onAccountToSelected(position)
        }

        binding.tvCountdown.addOnFinishCallback(viewModel::onCountdownFinish)

        binding.etAmountRemittance.addUserInputListener(viewModel::onAmountRemittanceInputted)
        binding.etAmountCollection.addUserInputListener(viewModel::onAmountCollectionInputted)
    }

    private fun subscribeEvents() {
        viewModel.updateAccounts.observe(this, Observer {
            updateAccounts(it)
        })
    }

    private fun updateAccounts(accounts: List<Account>) {
        binding.accountToChooser.accounts = accounts
        binding.accountFromChooser.accounts = accounts
    }

}