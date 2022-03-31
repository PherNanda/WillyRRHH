package com.android.willyrrhh.data.model

data class OompaLoompasPageResponse(
    val current: Int,
    val results: List<OompaLoompaPageItem>,
    val total: Int
)
