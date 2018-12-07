package com.storiqa.storiqawallet.di.modules

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.storiqa.storiqawallet.di.qualifiers.ActivityContext
import com.storiqa.storiqawallet.di.scopes.PerActivity
import com.storiqa.storiqawallet.network.errors.ErrorHandler
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

    @Provides
    @PerActivity
    internal fun provideErrorHandler(@ActivityContext context: Context): ErrorHandler = ErrorHandler(context)

}