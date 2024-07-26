package com.dmm.bootcamp.yatter2024.ui.profile

import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.StatusBindingModel

class ProfileUiState(
    val accountBindingModel: AccountBindingModel?,
    val statusList: List<StatusBindingModel>,
    val mine: Boolean,
    val isLoading: Boolean,
) {
    companion object {
        fun empty(): ProfileUiState = ProfileUiState(
            accountBindingModel = null,
            statusList = emptyList(),
            mine = false,
            isLoading = false,
        )
    }
}