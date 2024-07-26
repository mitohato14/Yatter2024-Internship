package com.dmm.bootcamp.yatter2024.usecase.edit

sealed interface EditUseCaseResult {
    object Success : EditUseCaseResult
    sealed interface Failure : EditUseCaseResult {
        object EmptyUsername : Failure
        object EmptyPassword : Failure
        object InvalidPassword : Failure
        data class OtherError(val throwable: Throwable) : Failure
    }
}
