package com.storiqa.storiqawallet.ui.password.setup

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentPasswordSetupBinding

class PasswordSetupFragment : Fragment() {

    private lateinit var binding: FragmentPasswordSetupBinding
    private lateinit var viewModel: PasswordSetupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_password_setup, container, false)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

    private fun obtainViewModel(): PasswordSetupViewModel =
            ViewModelProviders.of(this).get(PasswordSetupViewModel::class.java)
}