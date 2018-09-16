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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Dexter.withActivity(activity).withPermission(Manifest.permission.READ_CONTACTS).withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                viewModel.requestContacts()
            }

            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }).check()

        viewModel.contacts.observe(this, Observer{
            rvContacts.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = ContactsAdapter(it!!, {
                    //TODO set wallet
                })
            }
        })
    }
}