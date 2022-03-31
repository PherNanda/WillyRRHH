package com.android.willyrrhh.data

import com.android.willyrrhh.BuildConfig
import com.android.willyrrhh.data.service.WillyApi
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.Logger
import com.ihsanbal.logging.LoggingInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import com.google.gson.GsonBuilder


class ApiProvider {

    private val httpClient: OkHttpClient
    private val retrofit: Retrofit
    val willyApi: WillyApi

    init {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val builder = OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addNetworkInterceptor { chain ->
                chain.proceed(
                    chain.request()
                        .newBuilder()
                        .header("Accept", "application/json")
                        .method(chain.request().method, chain.request().body)
                        .header(
                            "User-Agent",
                            "Android:${BuildConfig.WILLY_APP} Version:${BuildConfig.VERSION_NAME} Package:${BuildConfig.PACKAGE_NAME} Framework:$TAG/4.4.0"
                        )
                        .build()
                )
            }
        val loggingInterceptor = LoggingInterceptor.Builder()
            .log(Platform.INFO)
            .setLevel(Level.BASIC)
            .logger(object : Logger {
                override fun log(level: Int, tag: String?, msg: String?) {
                    Timber.tag(TAG).d(msg)
                }
            })
            .build()
        builder.addInterceptor(loggingInterceptor)


        httpClient = builder.build()
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(httpClient)
            //.addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        willyApi = retrofit.create(WillyApi::class.java)

    }

    companion object {
        const val CONNECTION_TIMEOUT_SECONDS = 30L
        const val READ_TIMEOUT_SECONDS = 120L
        const val WRITE_TIMEOUT_SECONDS = 120L

        private const val TAG = "OkHttp"
    }
}