package com.android.willyrrhh.data.repository

import com.android.willyrrhh.data.model.OompaLoompasPageResponse
import com.android.willyrrhh.data.service.WillyApi

class OompaLoompaRepository (private val willyApi: WillyApi): DefaultRepository{


    override suspend fun fetchAllOompaLoompas(
        page: Int
    ): OompaLoompasPageResponse? {
        return willyApi.getAllOompaLoompas(
            page = page
        ).body()
    }
}