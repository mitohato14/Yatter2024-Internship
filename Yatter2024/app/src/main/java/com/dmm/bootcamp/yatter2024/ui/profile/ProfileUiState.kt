package com.dmm.bootcamp.yatter2024.ui.profile

import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.ProfileBindingModel

data class ProfileUiState (
    var ProfileBindingModel: ProfileBindingModel,
    var username : String,
    val isLoading: Boolean,
    val isRefreshing: Boolean,
    val isMe: Boolean,
) {
    companion object {
        fun empty(): ProfileUiState = ProfileUiState(
            ProfileBindingModel = ProfileBindingModel(
                id = "",
                displayName = "",
                username = "",
                note = "",
                avatar = "",
                header = "",
                followingCount = 0,
                followerCount = 0,
            ),
            username = "",
            isLoading = false,
            isRefreshing = false,
            isMe = false,
        )
    }
}