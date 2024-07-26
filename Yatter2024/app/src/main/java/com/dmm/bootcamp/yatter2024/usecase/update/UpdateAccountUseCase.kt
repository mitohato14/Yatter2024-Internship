package com.dmm.bootcamp.yatter2024.usecase.update

import java.net.URL

interface UpdateAccountUseCase {
    suspend fun execute(
        displayName: String?,
        note: String?,
        avatar: URL?,
        header: URL?,
    ): UpdateAccountUseCaseResult
}