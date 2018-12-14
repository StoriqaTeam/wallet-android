package com.storiqa.storiqawallet.di.modules

import android.support.v4.app.Fragment
import dagger.Module

@Module
class FragmentModule(private val fragment: Fragment) {

    /*@Provides
    @PerFragment
    internal fun provideFragmentNavigator(): FragmentNavigator {
        return ChildFragmentNavigator(fragment)
    }*/

}