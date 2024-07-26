package com.dmm.bootcamp.yatter2024.usecase.edit

import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username

interface EditUseCase {
    suspend fun execute(
        username: Username,
        password: Password,
    ): EditUseCaseResult
}