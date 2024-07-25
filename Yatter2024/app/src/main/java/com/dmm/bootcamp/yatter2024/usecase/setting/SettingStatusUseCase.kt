package com.dmm.bootcamp.yatter2024.usecase.setting

import com.dmm.bootcamp.yatter2024.domain.model.Account
import com.dmm.bootcamp.yatter2024.domain.model.Me
import java.io.File
import java.net.URL

interface SettingStatusUseCase {
    suspend fun execute(
        me: Me?,
        newDisplayName: String?,
        newNote: String?,
        newAvatar: File?,
        newHeader: File?,
    ) : SettingStatusUseCaseResult
}