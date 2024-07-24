package com.dmm.bootcamp.yatter2024.ui.profile
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileBindingModel
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileUiState

data class ProfileUiState(
    val bindingModel: ProfileBindingModel,
    val isLoading: Boolean,
) {
    companion object {
        fun empty(): ProfileUiState = ProfileUiState(
            bindingModel = ProfileBindingModel(
                avatarUrl = null,
                statusText = ""
            ),
            isLoading = false,
        )
    }
}