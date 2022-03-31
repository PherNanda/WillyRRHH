package com.android.willyrrhh.di

import com.android.willyrrhh.data.ApiProvider
import com.android.willyrrhh.data.repository.OompaLoompaRepository
import com.squareup.moshi.Moshi
import org.koin.dsl.module

val appModule = module {
    single {
        ApiProvider()
    }

    single {
        Moshi.Builder().build()
    }

    factory {
        OompaLoompaRepository(
            willyApi = get<ApiProvider>().willyApi
        )
    }

}