package com.dmm.bootcamp.yatter2024.infra.api.json

import com.squareup.moshi.Json
import okhttp3.MultipartBody


data class UpdateAccountJson(
    @Json(name = "display_name") val displayName: String?,
    @Json(name = "note") val note: String?,
    @Json(name = "avatar") val avatar: MultipartBody.Part?,
    @Json(name = "header") val header: MultipartBody.Part?,
)
