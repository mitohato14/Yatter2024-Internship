package com.dmm.bootcamp.yatter2024.usecase.profile

import java.io.File

interface ProfileStatusUseCase {
    suspend fun execute(
        content: String,
        attachmentList: List<File>
    ): ProfileStatusUseCaseResult
}