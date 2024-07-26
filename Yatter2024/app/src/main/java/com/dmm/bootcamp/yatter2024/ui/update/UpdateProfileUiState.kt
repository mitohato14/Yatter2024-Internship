package com.dmm.bootcamp.yatter2024.ui.update

import com.dmm.bootcamp.yatter2024.ui.update.bindingmodel.ProfileUpdateBindingModel

data class UpdateProfileUiState(
    val profileUpdateBindingModel: ProfileUpdateBindingModel,
    val username: String,
    val isLoading: Boolean,
) {
    companion object {
        fun empty(): UpdateProfileUiState = UpdateProfileUiState(
            profileUpdateBindingModel = ProfileUpdateBindingModel(
                displayName = "",
                note = "",
                avatar = null,
                header = null
            ),
            username = "",
            isLoading = false,
        )
    }
}
