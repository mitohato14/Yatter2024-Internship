package com.dmm.bootcamp.yatter2024.usecase.image

import com.dmm.bootcamp.yatter2024.domain.model.MediaId

sealed interface ImageStatusUseCaseResult {
    data class Success(
        val id : MediaId,
        val url : String,
    ) : ImageStatusUseCaseResult
    sealed interface Failure : ImageStatusUseCaseResult{
        data class OtherError(val throwable:Throwable) : Failure
    }
}