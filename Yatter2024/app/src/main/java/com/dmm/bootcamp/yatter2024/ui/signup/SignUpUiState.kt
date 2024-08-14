package com.dmm.bootcamp.yatter2024.ui.signup

import com.dmm.bootcamp.yatter2024.ui.login.LoginBindingModel
import com.dmm.bootcamp.yatter2024.ui.login.LoginUiState

data class SignUpUiState(
    val signUpBindingModel: SignUpBindingModel,
    val isLoading: Boolean,

    val validUsername: Boolean,
){
    val isEnableSignUp: Boolean = validUsername

    companion object {
        fun empty(): SignUpUiState = SignUpUiState(
            signUpBindingModel = SignUpBindingModel(
                username = "",
                password = ""
            ),
            validUsername = false,
            isLoading = false,
        )
    }
}
