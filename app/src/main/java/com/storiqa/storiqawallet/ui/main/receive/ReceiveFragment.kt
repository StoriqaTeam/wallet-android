package com.storiqa.storiqawallet.ui.main.receive

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.databinding.FragmentReceiveBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.base.IBaseActivity
import com.storiqa.storiqawallet.ui.main.account.AccountCardSize
import com.storiqa.storiqawallet.ui.main.account.AccountPagerAdapter
import com.storiqa.storiqawallet.utils.convertDpToPx
import com.storiqa.storiqawallet.utils.shareImage


class ReceiveFragment : BaseFragment<FragmentReceiveBinding, ReceiveViewModel>() {

    private val clipDataLabel = "Blockchain address"

    private var accountsAdapter: AccountPagerAdapter? = null

    private var isRestoring = false

    override fun getLayoutId(): Int = R.layout.fragment_receive

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<ReceiveViewModel> = ReceiveViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        subscribeEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isRestoring = true
    }

    private fun initView() {
        (activity as IBaseActivity).setupActionBar(binding.toolbar)

        if (isRestoring) {
            initAccountsPager(viewModel.accounts)
            isRestoring = false
        }

        binding.etAddress.inputType = InputType.TYPE_NULL

        binding.imgQrCode.setOnLongClickListener { viewModel.onQrCodeClicked() }
    }

    private fun subscribeEvents() {
        viewModel.updateAccounts.observe(this, Observer {
            updateAccounts(it)
        })

        viewModel.shareQrCode.observe(this, Observer {
            val ctx = context ?: return@Observer
            shareImage(ctx, it)
        })

        viewModel.copyToClipboard.observe(this, Observer(::copyToClipboard))
    }

    private fun initAccountsPager(accounts: List<Account>) {
        accountsAdapter = AccountPagerAdapter(this@ReceiveFragment, accounts, AccountCardSize.MEDIUM)
        binding.accountsPager.apply {
            adapter = accountsAdapter
            setPadding(convertDpToPx(30f).toInt(), 0, convertDpToPx(30f).toInt(), 0)
            clipToPadding = false
            pageMargin = convertDpToPx(20f).toInt()
            setCurrentItem(viewModel.currentPosition, false)
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

    private fun copyToClipboard(address: String) {
        val ctx = context ?: return
        val clipboard = ContextCompat.getSystemService(ctx, ClipboardManager::class.java) as ClipboardManager
        val clip = ClipData.newPlainText(clipDataLabel, address)
        clipboard.primaryClip = clip

        Toast.makeText(ctx, R.string.toast_address_copied, Toast.LENGTH_SHORT).show()
    }

}