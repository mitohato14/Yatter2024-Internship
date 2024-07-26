package com.dmm.bootcamp.yatter2024.usecase.update

open class UpdateAccountUseCaseResult {
    object Success : UpdateAccountUseCaseResult()
    sealed class Failure : UpdateAccountUseCaseResult() {
        object NotFoundAccount : Failure()
        data class UpdateAccountError(val throwable: Throwable) : Failure()
    }
}