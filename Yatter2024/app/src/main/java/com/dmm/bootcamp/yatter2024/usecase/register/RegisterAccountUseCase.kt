package com.dmm.bootcamp.yatter2024.usecase.register

import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username

interface RegisterAccountUseCase {
  suspend fun execute(
    username: String,
    password: String
  ): RegisterAccountUseCaseResult
}
