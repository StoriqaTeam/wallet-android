package com.storiqa.storiqawallet.screen_main.my_wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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
import kotlinx.android.synthetic.main.fragment_wallet_transactions.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.dip


class WalletLastTransactionsFragment : Fragment() {

    lateinit var viewModel : MainActivityViewModel
    lateinit var bills : Array<Bill>
    val maxAmountOfTransactions = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
        bills = arguments?.getSerializable(Extras().bill) as Array<Bill>
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_wallet_transactions, container, false)

        view.vpBills.adapter = BillsPagerAdapter(childFragmentManager!!, bills)
        view.vpBills.adapter?.notifyDataSetChanged()
        view.vpBills.clipToPadding = false
        view.vpBills.setPadding(dip(20),0, dip(20),0)
        view.pageIndicator.setupWithViewPager(view.vpBills, true)
        view.vpBills.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

            override fun onPageSelected(pageNumber: Int) {
                viewModel.updateTransactionList(bills[pageNumber].id, maxAmountOfTransactions)
            }
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnBack.onClick { viewModel.goBack() }
        tvViewAll.onClick {
            fragmentManager?.let {
                val walletAllTransactionsFragment = WalletAllTransactionsFragment()
                it.beginTransaction().replace(R.id.flWallet, walletAllTransactionsFragment)
                        .addToBackStack("")
                        .commit() }
        }

        val selectedBill = bills.first { it.id == arguments?.getString(Extras().idOfSelectedBill) }
        vpBills.currentItem = bills.indexOf(selectedBill)
    }

    override fun onResume() {
        super.onResume()
        viewModel.transactions.observe(this, Observer<Array<Transaction>> { newTransactions ->
            rvTransactions.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = TransactionAdapter(newTransactions!!, {})
            }
        })
        viewModel.updateTransactionList(bills[0].id, maxAmountOfTransactions)
    }

    override fun onPause() {
        viewModel.transactions.removeObserver {  }
        super.onPause()
    }

    companion object {
        fun getInstance(idOfSelectedBill : String, bills : Array<Bill>) : WalletLastTransactionsFragment {
            val walletTransactionsFragment = WalletLastTransactionsFragment()
            val bundle = Bundle()
            bundle.putSerializable(Extras().bill, bills)
            bundle.putString(Extras().idOfSelectedBill, idOfSelectedBill)
            walletTransactionsFragment.arguments = bundle
            return walletTransactionsFragment
        }
    }

}