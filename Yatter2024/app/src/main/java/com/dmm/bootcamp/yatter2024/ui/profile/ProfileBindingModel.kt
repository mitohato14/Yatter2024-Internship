package com.dmm.bootcamp.yatter2024.ui.profile

import com.dmm.bootcamp.yatter2024.domain.model.AccountId
import com.dmm.bootcamp.yatter2024.domain.model.Username

data class ProfileBindingModel(
    val username: String,
    val numPost : Int?,
    val numFollow : Int?,
    val numFollower : Int?,
)