package com.dmm.bootcamp.yatter2024.ui.profile

import com.dmm.bootcamp.yatter2024.common.ddd.Identifier
import com.dmm.bootcamp.yatter2024.domain.model.AccountId
import com.dmm.bootcamp.yatter2024.domain.model.Username
import java.net.URL

data class ProfileBindingModel(
    val myname: String?,
    val username: String,
    val note: String?,
    val numPost : Int?,
    val numFollow : Int?,
    val numFollower : Int?,
    val avatar: String?,
    val header: String?,
    val id: String?,
)