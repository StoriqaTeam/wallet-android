package com.storiqa.storiqawallet.screen_main.send

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentSendBinding
import com.storiqa.storiqawallet.objects.BillPagerHelper
import com.storiqa.storiqawallet.screen_main.MainActivityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_send.*
import kotlinx.android.synthetic.main.tab_icon.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.math.BigDecimal
import java.util.concurrent.TimeUnit


class SendFragment : Fragment() {

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binder = FragmentSendBinding.inflate(inflater, container, false)
        binder.viewModel = viewModel
        binder.executePendingBindings()

        BillPagerHelper(childFragmentManager) { pageNumber ->
            viewModel.selectedBillId = viewModel.bills.value!![pageNumber].id
        }.setPager(binder.vpBills, binder.pageIndicator, viewModel.bills.value!!, viewModel.selectedBillId)

        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTokenTypeInfo()
        observeAmountChanging()
        observeCurrencyChangeRecalculated()
        refreshAmountInStq()

        btnNext.onClick {
            viewModel.openRecieverScreen()
        }

        addTab(R.drawable.stq_small_logo_off_2x, R.drawable.stq_small_logo_2x)
        addTab(R.drawable.btc_small_logo_off_2x, R.drawable.btc_small_logo_on)
        addTab(R.drawable.eth_small_logo_off_2x, R.drawable.eth_small_logo_on_2x)
    }

    private fun addTab(logoOff: Int, logoOn: Int) {
        val view = LayoutInflater.from(context).inflate(R.layout.tab_icon, null, false)
        view.iconOff.setBackgroundResource(logoOff)
        view.iconOn.setBackgroundResource(logoOn)

        val tab = tlPaymentVariants.newTab()
        tab.customView = view
        tlPaymentVariants.addTab(tab)
    }

    private fun observeCurrencyChangeRecalculated() {
        viewModel.amountInSTQ.observe(this, Observer<String> { tvAmountInSTQ.text = "~$it STQ" })
    }

    private fun observeAmountChanging() {
        RxTextView.afterTextChangeEvents(etAmount)
                .observeOn(AndroidSchedulers.mainThread()).subscribe { btnNext.isEnabled = etAmount.text.isNotEmpty() }

        RxTextView.afterTextChangeEvents(etAmount).skipInitialValue().debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe { refreshAmountInStq() }
    }

    private fun setTokenTypeInfo() {
        tlPaymentVariants.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {
                p0?.customView?.iconOn?.visibility = View.GONE
                p0?.customView?.iconOff?.visibility = View.VISIBLE
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.tokenType.set(when (tlPaymentVariants.selectedTabPosition) {
                    0 -> "STQ"
                    1 -> "BTC"
                    2 -> "ETH"
                    else -> ""
                })

                tab?.customView?.iconOn?.visibility = View.VISIBLE
                tab?.customView?.iconOff?.visibility = View.GONE
                refreshAmountInStq()
            }
        })
    }

    fun refreshAmountInStq() {
        if(etAmount != null) {
            if (etAmount.text.isEmpty()) {
                viewModel.amountInSTQ.value = "0"
            } else {
                viewModel.refreshAmountInStq(viewModel.tokenType.get()!!, BigDecimal(etAmount.text.toString()))
            }
        }
    }
}