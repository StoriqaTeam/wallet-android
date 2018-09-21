package com.storiqa.storiqawallet.screen_main.send

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentSendFinalBinding
import com.storiqa.storiqawallet.enums.Currency
import com.storiqa.storiqawallet.enums.Screen
import com.storiqa.storiqawallet.screen_main.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_send_final.*
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
                currencyLogo.setImageResource(R.drawable.stq_medium_logo)
                tvAmountInSTQ.visibility = View.GONE
            }
            Currency.ETH.name-> currencyLogo.setImageResource(R.drawable.eth_medium_logo)
            Currency.BTC.name -> currencyLogo.setImageResource(R.drawable.bitcoin_medium_logo)
        }

        btnBack.onClick {
            viewModel.goBack()
        }

        val minFee = 20.0
        val maxFee = 3000.0
        val minWait = 8.0
        val maxWait = 500.0

        tvWaitTime.text = minWait.toString() + " s"

        tvCommission.text = minFee.toString()
        sbFee.max = maxFee.toInt()
        sbFee.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                var fee = minFee
                if(progress > minFee) {
                    fee = progress.toDouble()
                    tvCommission.text = progress.toString() + " STQ"
                }

                var waitTime = maxWait * (1.0 * progress / maxFee)
                if(waitTime < minWait) waitTime = minWait

                tvWaitTime.text = waitTime.toInt().toString() + " s"

                val subtotal = viewModel.amountInSTQ.value!!.toDouble() + fee
                tvSubtotal.text = subtotal.toString() + " STQ"

                val billId = viewModel.selectedBillId
                val bill = viewModel.bills.value!!.filter { it.id == billId }[0]
                if(bill.amount.replace(",","").toDouble() < subtotal) {
                    btnSend.isEnabled = false
                    tvError.visibility = View.VISIBLE
                } else {
                    btnSend.isEnabled = true
                    tvError.visibility = View.GONE
                }

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

//        btnSend.onClick {
//            AlertDialog.Builder(this).setView().show()
//        }
    }
}