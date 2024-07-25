package com.dmm.bootcamp.yatter2024.ui.profile

import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.MediaBindingModel
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.StatusBindingModel

data class ProfileUiState(
    val statusList: List<StatusBindingModel>,
    val statusBindingModel: StatusBindingModel,
    val isLoading: Boolean,
    val isRefreshing: Boolean,
) {
    companion object {
        fun empty(): ProfileUiState = ProfileUiState(
            statusList = emptyList(),
            statusBindingModel = StatusBindingModel(
                 id = "",
                 displayName = "",
                 username = "",
                 avatar = null,
                 content = "",
                 attachmentMediaList = emptyList()
            ),
            isLoading = false,
            isRefreshing = false,
        )
    }
}