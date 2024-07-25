package com.dmm.bootcamp.yatter2024.ui.profile

import com.dmm.bootcamp.yatter2024.domain.model.AccountId
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.StatusBindingModel

data class ProfileUiState(
    val profileBindingModel: ProfileBindingModel,
    val statusList: List<StatusBindingModel>,
    val isLoading: Boolean,
    val isRefreshing: Boolean,
){
    companion object{
        fun empty(): ProfileUiState = ProfileUiState(
            statusList = emptyList(),
            profileBindingModel = ProfileBindingModel(
                username = "",
                numPost = 0,
                numFollow = 0,
                numFollower = 0,
            ),
            isLoading = false,
            isRefreshing = false,
        )
    }
}
