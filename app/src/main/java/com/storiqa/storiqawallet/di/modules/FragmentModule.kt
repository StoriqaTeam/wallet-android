package com.storiqa.storiqawallet.di.modules

import dagger.Module

@Module
class FragmentModule(private val fragment: androidx.fragment.app.Fragment) {

    /*@Provides
    @PerFragment
    internal fun provideFragmentNavigator(): FragmentNavigator {
        return ChildFragmentNavigator(fragment)
    }*/

}