package com.storiqa.storiqawallet.screen_main.my_wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.adapters.TransactionAdapter
import com.storiqa.storiqawallet.objects.Transaction
import com.storiqa.storiqawallet.screen_main.MainActivityViewModel
import kotlinx.android.synthetic.main.transaction_list_fragment.*

class WalletAllTransactionsFragment : Fragment() {
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.transaction_list_fragment, container, false)
        //view.btnBack.onClick { viewModel.goBack() }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.transactions.observe(this, Observer<Array<Transaction>> { newTransactions ->
            rvTransactions.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = TransactionAdapter(newTransactions!!, {})
            }
        })
        viewModel.updateTransactionList(viewModel.selectedBillId)
    }
}