package com.storiqa.storiqawallet.ui.dialogs

import android.app.Dialog
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.android.databinding.library.baseAdapters.BR
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.databinding.DialogMessageBinding
import com.storiqa.storiqawallet.di.components.DaggerFragmentComponent
import com.storiqa.storiqawallet.di.components.FragmentComponent
import com.storiqa.storiqawallet.di.modules.FragmentModule
import com.storiqa.storiqawallet.ui.base.BaseActivity
import javax.inject.Inject


const val ARGUMENT_TITLE = "argument_title"
const val ARGUMENT_MESSAGE = "argument_message"
const val ARGUMENT_ICON = "argument_icon"

class MessageDialog : DialogFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MessageViewModel

    internal val fragmentComponent: FragmentComponent by lazy {
        DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule(this))
                .activityComponent((activity as BaseActivity<*, *>).activityComponent)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            FragmentComponent::class.java.getDeclaredMethod("inject", this::class.java)
                    .invoke(fragmentComponent, this)
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(MessageViewModel::class.java)
        } catch (e: NoSuchMethodException) {
            throw NoSuchMethodException("You forgot to add \"fun inject(activity: " +
                    "${this::class.java.simpleName})\" in ActivityComponent")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)

        viewModel.initData(
                App.res.getString(arguments?.getInt(ARGUMENT_TITLE)!!),
                App.res.getString(arguments?.getInt(ARGUMENT_MESSAGE)!!),
                App.res.getDrawable(arguments?.getInt(ARGUMENT_ICON)!!))

        val binding = DialogMessageBinding.inflate(activity!!.layoutInflater)
        binding.setVariable(BR.viewModel, viewModel)
        builder.setView(binding.root)
        binding.executePendingBindings()

        return builder.create()
    }
}