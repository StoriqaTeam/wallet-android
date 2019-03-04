package com.storiqa.storiqawallet.ui.main.receive

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.databinding.FragmentReceiveBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.base.IBaseActivity
import com.storiqa.storiqawallet.utils.shareImage


class ReceiveFragment : BaseFragment<FragmentReceiveBinding, ReceiveViewModel>() {

    private val clipDataLabel = "Blockchain address"

    override fun getLayoutId(): Int = R.layout.fragment_receive

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<ReceiveViewModel> = ReceiveViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        subscribeEvents()
    }

    private fun initView() {
        (activity as IBaseActivity).setupActionBar(binding.toolbar)

        binding.accountChooser.init(requireContext(), viewModel.currentPosition)
        binding.accountChooser.setOnPageSelectedListener { position, _ ->
            viewModel.onAccountSelected(position)
        }

        binding.etAddress.inputType = InputType.TYPE_NULL

        binding.imgQrCode.setOnLongClickListener { viewModel.onQrCodeClicked() }
    }

    private fun subscribeEvents() {
        viewModel.accounts.observe(this, Observer { updateAccounts(it) })

        viewModel.shareQrCode.observe(this, Observer {
            val ctx = context ?: return@Observer
            shareImage(ctx, it)
        })

        viewModel.copyToClipboard.observe(this, Observer(::copyToClipboard))
    }

    private fun updateAccounts(accounts: List<Account>) {
        binding.accountChooser.accounts = accounts
    }

    private fun copyToClipboard(address: String) {
        val ctx = context ?: return
        val clipboard = ContextCompat.getSystemService(ctx, ClipboardManager::class.java) as ClipboardManager
        val clip = ClipData.newPlainText(clipDataLabel, address)
        clipboard.primaryClip = clip

        Toast.makeText(ctx, R.string.toast_address_copied, Toast.LENGTH_SHORT).show()
    }

}