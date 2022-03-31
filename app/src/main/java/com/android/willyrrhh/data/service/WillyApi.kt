package com.android.willyrrhh.data.service

import com.android.willyrrhh.data.model.OompaLoompa
import com.android.willyrrhh.data.model.OompaLoompasPageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WillyApi {

    @GET("oompa-loompas")
    suspend fun getAllOompaLoompas(
        @Query("page") page: Int
    ): Response<OompaLoompasPageResponse>

    @GET("oompa-loompas/{id}")
    suspend fun getOneOompaLoompa(
        @Path("id") id: Int = 1
    ): Response<OompaLoompa>

}