package com.kamilagcabay.koincryptocrazyv2.Di

import com.kamilagcabay.koincryptocrazyv2.Repository.CryptoDownload
import com.kamilagcabay.koincryptocrazyv2.Repository.CryptoDownloadImpl
import com.kamilagcabay.koincryptocrazyv2.Service.CryptoAPI
import com.kamilagcabay.koincryptocrazyv2.ViewModel.CryptoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule = module {


    single {

        val BASE_URL = "https://raw.githubusercontent.com/"
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)

    }

    single<CryptoDownload> {

        CryptoDownloadImpl(get())

    }

    viewModel {
        CryptoViewModel(get())
    }

}