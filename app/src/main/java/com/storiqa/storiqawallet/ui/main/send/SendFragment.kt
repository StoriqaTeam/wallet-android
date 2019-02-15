package com.storiqa.storiqawallet.ui.main.send

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.google.zxing.integration.android.IntentIntegrator
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.data.model.AccountCardSize
import com.storiqa.storiqawallet.databinding.FragmentSendBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.base.IBaseActivity
import com.storiqa.storiqawallet.ui.common.SpacesWatcher
import com.storiqa.storiqawallet.ui.common.addUserInputListener
import com.storiqa.storiqawallet.ui.main.account.AccountPagerAdapter
import com.storiqa.storiqawallet.utils.convertDpToPx


class SendFragment : BaseFragment<FragmentSendBinding, SendViewModel>() {

    private var accountsAdapter: AccountPagerAdapter? = null

    private var isRestoring = false

    override fun getLayoutId(): Int = R.layout.fragment_send

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<SendViewModel> = SendViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        subscribeEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isRestoring = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                viewModel.onQrCodeScanned(result.contents)
                return
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initView() {
        (activity as IBaseActivity).setupActionBar(binding.toolbar)

        if (isRestoring) {
            initAccountsPager(viewModel.accounts)
            isRestoring = false
        }

        binding.etAddress.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus)
                    viewModel.validateAddress()
            }
            addTextChangedListener(SpacesWatcher(binding.etAddress))
        }

        binding.etAmountCrypto.addUserInputListener(viewModel::calculateAmountFiat)
        binding.etAmountFiat.addUserInputListener(viewModel::calculateAmountCrypto)
    }

    private fun subscribeEvents() {
        viewModel.updateAccounts.observe(this, Observer {
            updateAccounts(it)
        })

        viewModel.scanQrCode.observe(this, Observer {
            scanQrCode()
        })

        viewModel.showInvalidAddressError.observe(this, Observer {
            Toast.makeText(context, getText(R.string.toast_address_invalid), Toast.LENGTH_SHORT).show()
        })
    }

    private fun initAccountsPager(accounts: List<Account>) {
        accountsAdapter = AccountPagerAdapter(this@SendFragment, accounts, AccountCardSize.MEDIUM)
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

    private fun scanQrCode() {
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt(getString(R.string.text_scan))
        integrator.setBeepEnabled(false)
        integrator.initiateScan()
    }

}