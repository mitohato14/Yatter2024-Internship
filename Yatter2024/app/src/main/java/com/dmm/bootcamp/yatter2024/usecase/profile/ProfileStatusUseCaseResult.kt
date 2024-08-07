package com.dmm.bootcamp.yatter2024.usecase.profile

sealed interface ProfileStatusUseCaseResult {
    object Success : ProfileStatusUseCaseResult
    sealed interface Failure : ProfileStatusUseCaseResult {
        object EmptyContent : Failure
        object NotLoggedIn : Failure
        data class OtherError(val throwable: Throwable) : Failure
    }
}