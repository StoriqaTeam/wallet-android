package com.storiqa.storiqawallet.screen_main.send

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.storiqa.storiqawallet.adapters.ContactsAdapter
import com.storiqa.storiqawallet.databinding.FragmentChooseRecieverBinding
import com.storiqa.storiqawallet.objects.Contact
import com.storiqa.storiqawallet.screen_main.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_choose_reciever.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import android.content.Intent
import com.blikoon.qrcodescanner.QrCodeActivity
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.constants.RequestCodes
import com.storiqa.storiqawallet.enums.Currency
import kotlinx.android.synthetic.main.item_bill.*
import java.util.*

class ChooseRecieverFragment : Fragment() {

    lateinit var viewModel : MainActivityViewModel
    lateinit var binder : FragmentChooseRecieverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binder = FragmentChooseRecieverBinding.inflate(inflater, container, false)
        binder.viewModel = viewModel
        binder.executePendingBindings()
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vClear.onClick {
            viewModel.clearSenderInfo()
            etReciever.text.clear()
        }

        RxTextView.afterTextChangeEvents(etReciever).skipInitialValue().subscribe {
            if(etReciever.text.isEmpty()) {
                setContacts(viewModel.getContacts())
                viewModel.clearSenderInfo()
            } else {
                val searchQuery = etReciever.text.toString()
                setContacts(viewModel.getContacts().filter { it.name.contains(searchQuery) || it.phone.startsWith(searchQuery) }.toTypedArray())
            }
        }

        ivStartScanner.onClick {
            Dexter.withActivity(activity).withPermission(Manifest.permission.CAMERA).withListener(object  : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    startActivityForResult(Intent(context, QrCodeActivity::class.java), RequestCodes().scanQR)
                }

                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {}
            }).check()
        }

        btnBack.onClick { viewModel.goBack() }

        viewModel.scannedQR.observe(this, Observer {
            it?.let { binder.etReciever.setText(it) }
        })

        btnNext.onClick {
            viewModel.openSendFinalScreen()
        }

        when(viewModel.tokenType.get()) {
            Currency.STQ.name-> {
                currencyLogo.setImageResource(R.drawable.bitcoin_medium_logo)
                tvAmountInSTQ.visibility = View.GONE
            }
            Currency.ETH.name-> currencyLogo.setImageResource(R.drawable.bitcoin_medium_logo)
            Currency.BTC.name -> currencyLogo.setImageResource(R.drawable.bitcoin_medium_logo)
        }
    }

    override fun onResume() {
        super.onResume()

        Dexter.withActivity(activity).withPermission(Manifest.permission.READ_CONTACTS).withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                viewModel.requestContacts()
                viewModel.contacts.observe(this@ChooseRecieverFragment, Observer{ newContacts ->
                    newContacts?.let { setContacts(newContacts)  }

                })
            }

            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                token?.continuePermissionRequest()
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {}

        }).check()
    }

    fun setContacts(newContacts: Array<Contact>) {

            rvContacts.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = ContactsAdapter(newContacts!!) {
                    binder.etReciever.setText(newContacts[it].phone)
                    setContacts(arrayOf(newContacts[it]))

                    viewModel.saveRecieverInfo(newContacts[it])
                }
                viewModel.isFoundErrorVisible.set(newContacts.isEmpty())
            }

    }
}