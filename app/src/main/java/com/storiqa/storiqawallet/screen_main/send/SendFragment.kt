package com.storiqa.storiqawallet.screen_main.send

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import com.storiqa.storiqawallet.databinding.FragmentSendBinding
import com.storiqa.storiqawallet.objects.BillPagerHelper
import com.storiqa.storiqawallet.screen_main.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_send.*
import kotlinx.android.synthetic.main.fragment_wallet_transactions.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

class SendFragment : Fragment() {

    lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binder = FragmentSendBinding.inflate(inflater, container, false)
        binder.executePendingBindings()
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        BillPagerHelper(childFragmentManager) { pageNumber ->
            viewModel.selectedBillId = viewModel.bills.value!![pageNumber].id
        }.setPager(view.vpBills, view.pageIndicator, viewModel.bills.value!!, viewModel.selectedBillId)

        var tokenType = "STQ"
        tlPaymentVariants.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tokenType = when(tlPaymentVariants.selectedTabPosition) {
                    0-> "STQ"
                    1-> "BTC"
                    2-> "ETH"
                    else -> ""
                }
                tvTokenType.text = tokenType
                viewModel.refreshAmountInStq(tokenType, BigDecimal(etAmount.text.toString()))
            }
        })

        tvTokenType.text = tokenType

        editLayout.onClick {
            etAmount.requestFocus()
        }

        RxTextView.afterTextChangeEvents(etAmount).skipInitialValue().debounce(500, TimeUnit.MILLISECONDS).subscribe {
            viewModel.refreshAmountInStq(tokenType, BigDecimal(etAmount.text.toString()))
        }

        viewModel.amountInSTQ.observe(this,
                android.arch.lifecycle.Observer<String> { newAmount -> tvAmountInSTQ.text = "~$newAmount" })
    }
}