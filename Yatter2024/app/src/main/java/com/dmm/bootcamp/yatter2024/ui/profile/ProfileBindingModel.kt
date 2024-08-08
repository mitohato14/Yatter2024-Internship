package com.dmm.bootcamp.yatter2024.ui.profile

import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.StatusBindingModel





data class PostBindingModel(
    val accountViewModel: AccountBindingModel,
    val homeStatusList: List<StatusBindingModel>
)