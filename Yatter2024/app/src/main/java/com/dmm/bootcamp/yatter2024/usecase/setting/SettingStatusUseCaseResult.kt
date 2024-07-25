package com.dmm.bootcamp.yatter2024.usecase.setting

sealed interface SettingStatusUseCaseResult {
    object Success : SettingStatusUseCaseResult
    sealed interface Failure : SettingStatusUseCaseResult{
        object NotLoggedIn : Failure
        data class OtherError(val throwable:Throwable) : Failure
    }
}