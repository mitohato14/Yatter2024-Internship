package com.dmm.bootcamp.yatter2024.ui.timelinedetail

import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.StatusBindingModel

data class TimelineDetailUiState(
    val statusBindingModel: StatusBindingModel,
    val isLoading: Boolean,
    val isRefreshing: Boolean,
) {
    companion object {
        fun empty(): TimelineDetailUiState = TimelineDetailUiState(
            statusBindingModel = StatusBindingModel(
                id = "",
                displayName = "",
                username = "",
                avatar = null,
                content = "",
                attachmentMediaList = listOf(),
            ),
            isLoading = false,
            isRefreshing = false,
        )
    }
}
