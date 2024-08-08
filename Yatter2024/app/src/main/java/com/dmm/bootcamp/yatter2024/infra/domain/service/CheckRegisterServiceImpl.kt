package com.dmm.bootcamp.yatter2024.infra.domain.service

import com.dmm.bootcamp.yatter2024.domain.service.CheckRegisterService
import com.dmm.bootcamp.yatter2024.infra.pref.TokenPreferences

class CheckRegisterServiceImpl (
    private val tokenPreferences: TokenPreferences,
): CheckRegisterService {
    override suspend fun execute(): Boolean {
        return tokenPreferences.getAccessToken().isNullOrEmpty().not()
    }
}