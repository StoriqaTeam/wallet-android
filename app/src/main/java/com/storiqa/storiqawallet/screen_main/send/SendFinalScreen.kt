package com.storiqa.storiqawallet.screen_main.send

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentSendFinalBinding
import com.storiqa.storiqawallet.enums.Currency
import com.storiqa.storiqawallet.enums.Screen
import com.storiqa.storiqawallet.screen_main.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_choose_reciever.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class SendFinalScreen : Fragment() {

    lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binder = FragmentSendFinalBinding.inflate(inflater, container, false)
        binder.viewModel = viewModel
        binder.executePendingBindings()
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when(viewModel.tokenType.get()) {
            Currency.STQ.name-> {
                currencyLogo.setImageResource(R.drawable.bitcoin_medium_logo)
                tvAmountInSTQ.visibility = View.GONE
            }
            Currency.ETH.name-> currencyLogo.setImageResource(R.drawable.bitcoin_medium_logo)
            Currency.BTC.name -> currencyLogo.setImageResource(R.drawable.bitcoin_medium_logo)
        }

        ivEdit.onClick {
            viewModel.onScreenChanged(Screen.SEND)
        }

        when(viewModel.tokenType.get()) {
            Currency.STQ.name-> {
                currencyLogo.setImageResource(R.drawable.stq_small_logo)
                tvAmountInSTQ.visibility = View.GONE
            }
            Currency.ETH.name-> currencyLogo.setImageResource(R.drawable.eth_small_logo_off_2x)
            Currency.BTC.name -> currencyLogo.setImageResource(R.drawable.btc_small_logo_off_2x)
        }
    }
}