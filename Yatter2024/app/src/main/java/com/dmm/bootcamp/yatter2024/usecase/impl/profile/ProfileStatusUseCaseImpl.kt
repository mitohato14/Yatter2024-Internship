package com.dmm.bootcamp.yatter2024.usecase.impl.profile

import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.service.LoginService
import com.dmm.bootcamp.yatter2024.infra.pref.MePreferences
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCaseResult
import com.dmm.bootcamp.yatter2024.usecase.profile.ProfileStatusUseCase
import com.dmm.bootcamp.yatter2024.usecase.profile.ProfileStatusUseCaseResult
import java.io.File

internal final class ProfileStatusUseCaseImpl(
    content: String,
    attachmentList: List<File>
) : ProfileStatusUseCase {
    override suspend fun execute(
        content: String,
        attachmentList: List<File>
    ): ProfileStatusUseCaseResult {
        TODO("Not yet implemented")
    }
    // ここに実装を記述する
    // 実際の処理に応じて、ProfileStatusUseCaseResultを返す必要があります
}