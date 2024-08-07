package com.dmm.bootcamp.yatter2024.ui.profile

import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.ProfileBindingModel
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.StatusBindingModel

data class ProfileUiState(
    val profileBindingModel: ProfileBindingModel,
    val statusList: List<StatusBindingModel>,
    val isLoading: Boolean,
    val isRefreshing: Boolean,
) {
    companion object {
        fun empty(): ProfileUiState = ProfileUiState(
            profileBindingModel = ProfileBindingModel(
                id = "",
                displayName = "",
                username = "",
                avatar = null,
                header = null,
                note = "",
                followingCount = 0,
                followerCount = 0,
            ),
            statusList = emptyList(),
            isLoading = false,
            isRefreshing = false,
        )
    }
}
