package com.storiqa.storiqawallet.di.modules

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.storiqa.storiqawallet.di.qualifiers.ActivityContext
import com.storiqa.storiqawallet.di.scopes.PerActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @PerActivity
    @ActivityContext
    internal fun provideActivityContext(): Context = activity

    @Provides
    @PerActivity
    internal fun provideFragmentManager(): FragmentManager = activity.supportFragmentManager

}