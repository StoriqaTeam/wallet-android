package com.storiqa.storiqawallet.ui.password.reset

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentPasswordResetBinding
import com.storiqa.storiqawallet.hideKeyboard

class PasswordResetFragment : Fragment() {

    private lateinit var binding: FragmentPasswordResetBinding
    private lateinit var viewModel: PasswordResetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_password_reset, container, false)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initObservers()

        return binding.root
    }

    private fun initObservers() {
        viewModel.hideKeyboard.observe(this, Observer { hideKeyboard() })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

    private fun obtainViewModel(): PasswordResetViewModel =
            ViewModelProviders.of(this).get(PasswordResetViewModel::class.java)
}