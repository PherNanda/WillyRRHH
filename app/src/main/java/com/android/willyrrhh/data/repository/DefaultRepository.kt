package com.android.willyrrhh.data.repository

import com.android.willyrrhh.data.model.OompaLoompa
import com.android.willyrrhh.data.model.OompaLoompasPageResponse

interface DefaultRepository {

    suspend fun fetchAllOompaLoompas(page: Int): OompaLoompasPageResponse?

    suspend fun fetchOneOompaLoompa(id: Int): OompaLoompa?
}