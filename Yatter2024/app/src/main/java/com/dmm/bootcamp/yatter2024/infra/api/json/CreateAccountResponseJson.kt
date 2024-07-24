package com.dmm.bootcamp.yatter2024.infra.api.json

import com.squareup.moshi.Json

data class CreateAccountResponseJson(
    @Json(name = "username") val accessToken: String,
)
