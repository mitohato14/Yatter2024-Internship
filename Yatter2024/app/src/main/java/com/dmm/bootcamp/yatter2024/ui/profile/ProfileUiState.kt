package com.dmm.bootcamp.yatter2024.ui.profile

import com.dmm.bootcamp.yatter2024.common.ddd.Identifier
import com.dmm.bootcamp.yatter2024.domain.model.AccountId
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.StatusBindingModel
import java.net.URL

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
                id = "",
                username = "",
                numPost = 0,
                numFollow = 0,
                numFollower = 0,
                avatar = "https://pbs.twimg.com/media/C8UHG9pUMAAoatJ.jpg",
                header = "https://cottoitalia.com/wp-content/uploads/2019/12/grey-brush-2.jpg",
            ),
            isLoading = false,
            isRefreshing = false,
        )
    }
}
