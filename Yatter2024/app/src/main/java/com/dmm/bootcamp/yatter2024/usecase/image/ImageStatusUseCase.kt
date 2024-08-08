package com.dmm.bootcamp.yatter2024.usecase.image

import java.io.File

interface ImageStatusUseCase {
    suspend fun execute(
        image: File?,
    ) : ImageStatusUseCaseResult
}