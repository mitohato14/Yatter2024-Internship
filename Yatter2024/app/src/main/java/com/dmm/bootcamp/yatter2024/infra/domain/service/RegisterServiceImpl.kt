package com.dmm.bootcamp.yatter2024.infra.domain.service

import com.dmm.bootcamp.yatter2024.domain.service.RegisterService
import com.dmm.bootcamp.yatter2024.infra.api.YatterApi
import com.dmm.bootcamp.yatter2024.infra.api.json.CreateAccountJson
import com.dmm.bootcamp.yatter2024.infra.pref.TokenPreferences

class RegisterServiceImpl(
    private val yatterApi: YatterApi,
) : RegisterService {
    override suspend fun execute(
        username: String,
        password: String,
    ){
        val requestJson = CreateAccountJson(
            username,
            password,
        )
        val response = yatterApi.createNewAccount(requestJson)
    }
}