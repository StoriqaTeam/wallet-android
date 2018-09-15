package com.storiqa.storiqawallet.screen_main.my_wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.adapters.BillsPagerAdapter
import com.storiqa.storiqawallet.adapters.TransactionAdapter
import com.storiqa.storiqawallet.constants.Extras
import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.objects.Transaction
import com.storiqa.storiqawallet.screen_main.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_wallet_transactions.*
import kotlinx.android.synthetic.main.header.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.dip


class WalletTransactionsFragment : Fragment() {

    lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_wallet_transactions, container, false)
        view.btnBack.onClick { viewModel.goBack() }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bills = arguments?.getSerializable(Extras().bill) as Array<Bill>
        vpBills.adapter = BillsPagerAdapter(fragmentManager!!, bills)
        vpBills.clipToPadding = false
        vpBills.setPadding(dip(20),0, dip(20),0)

        vpBills.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

            override fun onPageSelected(pageNumber: Int) {
                viewModel.updateTransactionList(bills[pageNumber].id)
            }
        })

        viewModel.transactions.observe(this, Observer<Array<Transaction>> { newTransactions ->
            rvTransactions.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = TransactionAdapter(newTransactions!!, {})
            }
        })
        viewModel.updateTransactionList(bills[0].id)
    }

    companion object {
        fun getInstance(idOfSelectedBill : String, bills : Array<Bill>) : WalletTransactionsFragment {
            val walletTransactionsFragment = WalletTransactionsFragment()
            val bundle = Bundle()
            bundle.putSerializable(Extras().bill, bills)
            bundle.putString(Extras().idOfSelectedBill, idOfSelectedBill)
            walletTransactionsFragment.arguments = bundle
            return walletTransactionsFragment
        }
    }

}