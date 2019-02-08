package com.storiqa.storiqawallet.ui.main.details

import android.os.Bundle
import android.view.View
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentTransactionDetailsBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.base.IBaseActivity

class TransactionDetailsFragment : BaseFragment<FragmentTransactionDetailsBinding, TransactionDetailsViewModel>() {

    companion object {
        const val KEY_ADDRESS = "key_address"
        const val KEY_TRANSACTION_ID = "key_transaction_id"
    }

    override fun getLayoutId(): Int = R.layout.fragment_transaction_details

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<TransactionDetailsViewModel> = TransactionDetailsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        val address = arguments?.getString(KEY_ADDRESS)!!
        val transactionId = arguments?.getString(KEY_TRANSACTION_ID)!!

        viewModel.loadTransaction(address, transactionId)
    }

    private fun initView() {
        (activity as IBaseActivity).setupActionBar(binding.toolbar, backButtonEnabled = true)
    }
}