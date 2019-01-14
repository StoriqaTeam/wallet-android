package com.storiqa.storiqawallet.screen_main.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.enums.Screen
import com.storiqa.storiqawallet.screen_main.MainActivityViewModel

class MenuFragment : Fragment() {

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
        viewModel.selectedScreen.set(Screen.MENU)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_menu_deprecated, null, false)
        //view.btnBack.onClick { viewModel.goBack() }
        return view
    }
}