package com.dmm.bootcamp.yatter2024.infra.api.json

import com.dmm.bootcamp.yatter2024.domain.model.MediaId
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediaResponseJson(
    @Json(name = "media_id") val id: Int,
    @Json(name = "type") val type: String?,
    @Json(name = "media_url") val url: String,
    @Json(name = "description") val description: String?
)
