package com.storiqa.storiqawallet.ui.main.details

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
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

    private val clipDataLabel = "Blockchain address"

    override fun getLayoutId(): Int = R.layout.fragment_transaction_details

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<TransactionDetailsViewModel> = TransactionDetailsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        val address = arguments?.getString(KEY_ADDRESS)!!
        val transactionId = arguments?.getString(KEY_TRANSACTION_ID)!!

        viewModel.loadTransaction(address, transactionId)

        viewModel.copyToClipboard.observe(this, Observer(::copyToClipboard))
    }

    private fun initView() {
        (activity as IBaseActivity).setupActionBar(binding.toolbar, backButtonEnabled = true)
    }

    private fun copyToClipboard(address: String) {
        val ctx = context ?: return
        val clipboard = getSystemService(ctx, ClipboardManager::class.java) as ClipboardManager
        val clip = ClipData.newPlainText(clipDataLabel, address)
        clipboard.primaryClip = clip

        Toast.makeText(ctx, R.string.toast_address_copied, Toast.LENGTH_SHORT).show()
    }
}