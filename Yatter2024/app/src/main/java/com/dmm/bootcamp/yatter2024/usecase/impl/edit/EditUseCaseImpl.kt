package com.dmm.bootcamp.yatter2024.usecase.impl.edit

import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.service.LoginService
import com.dmm.bootcamp.yatter2024.infra.pref.MePreferences
import com.dmm.bootcamp.yatter2024.usecase.edit.EditUseCase
import com.dmm.bootcamp.yatter2024.usecase.edit.EditUseCaseResult
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCaseResult

internal class EditUseCaseImpl(
    private val loginService: LoginService,
    private val mePreferences: MePreferences,
) : EditUseCase {
    override suspend fun execute(
        username: Username,
        password: Password
    ): EditUseCaseResult {
        try {
            if (username.value.isBlank()) return EditUseCaseResult.Failure.EmptyUsername
            if (password.value.isBlank()) return EditUseCaseResult.Failure.EmptyPassword

            if (!password.validate()) return EditUseCaseResult.Failure.InvalidPassword
            loginService.execute(username, password)
            mePreferences.putUserName(username.value)
            return EditUseCaseResult.Success
        } catch (e: Exception) {
            return EditUseCaseResult.Failure.OtherError(e)
        }
    }
}