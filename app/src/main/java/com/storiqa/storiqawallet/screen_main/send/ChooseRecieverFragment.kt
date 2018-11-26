package com.storiqa.storiqawallet.screen_main.send

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blikoon.qrcodescanner.QrCodeActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.adapters.ContactsAdapter
import com.storiqa.storiqawallet.constants.RequestCodes
import com.storiqa.storiqawallet.databinding.FragmentChooseRecieverBinding
import com.storiqa.storiqawallet.enums.Currency
import com.storiqa.storiqawallet.objects.Contact
import com.storiqa.storiqawallet.screen_main.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_choose_reciever.*
import kotlinx.android.synthetic.main.layout_ask_contacts.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class ChooseRecieverFragment : Fragment() {

    lateinit var viewModel: MainActivityViewModel
    lateinit var binder: FragmentChooseRecieverBinding

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
            if (etReciever.text.isEmpty()) {
                setContacts(viewModel.getContacts())
                viewModel.clearSenderInfo()
            } else {
                filterContacts()
            }
        }

        ivStartScanner.onClick { startScan() }
        tvStartScanText.onClick { startScan() }

        btnBack.onClick { viewModel.goBack() }

        btnNext.onClick { viewModel.openSendFinalScreen() }

        when (viewModel.tokenType.get()) {
            Currency.STQ.name -> {
                currencyLogo.setImageResource(R.drawable.stq_medium_logo)
                tvAmountInSTQ.visibility = View.GONE
            }
            Currency.ETH.name -> currencyLogo.setImageResource(R.drawable.eth_medium_logo)
            Currency.BTC.name -> currencyLogo.setImageResource(R.drawable.bitcoin_medium_logo)
        }


        ivEdit.onClick {
            viewModel.goBack()
        }

        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestAccessToContacts()
        } else {
            viewModel.requestContacts()
        }

        viewModel.contacts.observe(this@ChooseRecieverFragment, Observer { newContacts ->
            newContacts?.let { setContacts(newContacts) }
            if (etReciever.text.isNotEmpty()) {
                filterContacts()
            }
        })
    }

    override fun onResume() {
        super.onResume()

        if (viewModel.phone.get()!!.isNotEmpty() && etReciever.text.isEmpty()) {
            etReciever.setText(viewModel.phone.get()!!)
            filterContacts()
        }

        viewModel.scannedQR.observe(this, Observer {
            it?.let { binder.etReciever.setText(it) }
            viewModel.wallet.set(it)
            viewModel.reciever.set("Wallet owner")

            setAdapter(arrayOf())
            viewModel.isFoundErrorVisible.set(false)
            viewModel.isSentNextButtonEnabled.set(true)
            viewModel.isContinueButtonVisible.set(true)
        })
    }

    private fun startScan() {
        Dexter.withActivity(activity).withPermission(Manifest.permission.CAMERA).withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                startActivityForResult(Intent(context, QrCodeActivity::class.java), RequestCodes().scanQR)
            }

            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                token?.continuePermissionRequest()
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {}
        }).check()
    }

    private fun filterContacts() {
        val searchQuery = etReciever.text.toString()
        setContacts(viewModel.getContacts().filter { it.name.contains(searchQuery) || it.phone.contains(searchQuery) }.toTypedArray())
    }

    private fun requestAccessToContacts() {
        val view = layoutInflater.inflate(R.layout.layout_ask_contacts, null, false)

        val dialog = AlertDialog.Builder(context!!).setView(view).create()

        view.btnAllow.setOnClickListener {
            Dexter.withActivity(activity).withPermission(Manifest.permission.READ_CONTACTS).withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    viewModel.requestContacts()
                }

                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {}

            }).check()
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }

        view.tvDeny.setOnClickListener {
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    fun setContacts(newContacts: Array<Contact>) {
        tvError.visibility = View.INVISIBLE
        if (newContacts.size == 1) {
            viewModel.isContinueButtonVisible.set(true)
            if (newContacts[0].wallet.isNotEmpty()) {
                viewModel.isContinueButtonEnabled.set(true)
                viewModel.saveRecieverInfo(newContacts[0])
            } else {
                viewModel.isContinueButtonEnabled.set(false)
                tvError.visibility = View.VISIBLE
            }
        } else {
            viewModel.isContinueButtonVisible.set(false)
        }

        viewModel.isFoundErrorVisible.set(newContacts.isEmpty() && etReciever.text.isNotEmpty())

        setAdapter(newContacts)
    }

    fun setAdapter(newContacts: Array<Contact>) {
        rvContacts.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ContactsAdapter(newContacts!!) {
                binder.etReciever.setText(newContacts[it].phone)
                setContacts(arrayOf(newContacts[it]))
            }
        }
    }
}