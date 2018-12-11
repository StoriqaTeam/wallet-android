package com.storiqa.storiqawallet.di.components

import com.storiqa.storiqawallet.di.modules.FragmentModule
import com.storiqa.storiqawallet.di.modules.ViewModelModule
import com.storiqa.storiqawallet.di.scopes.PerFragment
import com.storiqa.storiqawallet.ui.dialogs.MessageDialog
import dagger.Component

@PerFragment
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(FragmentModule::class, ViewModelModule::class))
interface FragmentComponent : FragmentComponentProvides {

    fun inject(fragment: MessageDialog)
}

interface FragmentComponentProvides : ActivityComponentProvides {

}