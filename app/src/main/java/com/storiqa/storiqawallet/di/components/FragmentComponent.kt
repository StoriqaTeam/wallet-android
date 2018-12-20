package com.storiqa.storiqawallet.di.components

import com.storiqa.storiqawallet.di.modules.FragmentModule
import com.storiqa.storiqawallet.di.modules.ViewModelModule
import com.storiqa.storiqawallet.di.scopes.PerFragment
import com.storiqa.storiqawallet.ui.dialogs.MessageDialog
import com.storiqa.storiqawallet.ui.password.reset.PasswordResetFragment
import com.storiqa.storiqawallet.ui.password.setup.PasswordSetupFragment
import dagger.Component

@PerFragment
@Component(dependencies = arrayOf(ActivityComponent::class),
        modules = arrayOf(FragmentModule::class, ViewModelModule::class))
interface FragmentComponent : FragmentComponentProvides {

    fun inject(fragment: MessageDialog)

    fun inject(fragment: PasswordResetFragment)
    fun inject(fragment: PasswordSetupFragment)
}

interface FragmentComponentProvides : ActivityComponentProvides {

}