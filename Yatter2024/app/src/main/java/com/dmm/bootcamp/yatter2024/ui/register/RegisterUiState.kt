package com.dmm.bootcamp.yatter2024.ui.register

data class RegisterUiState(
    val bindingModel: RegisterBindingModel,
    val isLoading: Boolean,
) {
    companion object {
        fun empty(): RegisterUiState = RegisterUiState(
            bindingModel = RegisterBindingModel(
                username = "",
                password = ""
            ),
            isLoading = false,
        )
    }
}