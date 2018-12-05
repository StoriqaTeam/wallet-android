package com.storiqa.storiqawallet.di

import com.storiqa.storiqawallet.network.WalletApi
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val WEATHER_API_BASE_URL = "https://pay-nightly.stq.cloud/"

val apiModule = Kodein.Module("api") {

    bind<Retrofit>() with singleton {
        Retrofit.Builder()
                .baseUrl(WEATHER_API_BASE_URL)
                .client(OkHttpClient.Builder().build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    bind<WalletApi>() with singleton {
        val retrofit: Retrofit = instance()
        retrofit.create(WalletApi::class.java)
    }

}