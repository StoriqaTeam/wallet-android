package com.storiqa.storiqawallet.screen_main.exchange

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.enums.Screen
import com.storiqa.storiqawallet.screen_main.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_exchange.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class ExchangeFragment : Fragment() {

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
        viewModel.selectedScreen.set(Screen.EXCHANGE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_exchange, null, false)
        view.btnBack.onClick { viewModel.goBack() }
        return view
    }
}