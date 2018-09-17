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
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.storiqa.storiqawallet.adapters.ContactsAdapter
import com.storiqa.storiqawallet.databinding.FragmentChooseRecieverBinding
import com.storiqa.storiqawallet.screen_main.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_choose_reciever.*

class ChooseRecieverFragment : Fragment() {

    lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binder = FragmentChooseRecieverBinding.inflate(inflater, container, false)
        binder.viewModel = viewModel
        binder.executePendingBindings()
        return binder.root
    }

    override fun onResume() {
        super.onResume()

        Dexter.withActivity(activity).withPermission(Manifest.permission.READ_CONTACTS).withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                viewModel.requestContacts()
                viewModel.contacts.observe(this@ChooseRecieverFragment, Observer{ newContacts ->
                    rvContacts.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(context)
                        adapter = ContactsAdapter(newContacts!!) {
                            etReciever.setText(viewModel.contacts.value!![it].wallet)
                        }
                    }
                })
            }

            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                token?.continuePermissionRequest()
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {

            }

        }).check()
    }
}