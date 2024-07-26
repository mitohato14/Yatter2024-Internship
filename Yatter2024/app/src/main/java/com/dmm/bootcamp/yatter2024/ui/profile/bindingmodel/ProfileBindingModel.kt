package com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel

import com.dmm.bootcamp.yatter2024.domain.model.Username
import java.net.URL

data class MediaBindingModel(
    val id: String,
    val type: String,
    val url: String,
    val description: String?
)

data class StatusBindingModel(
    val id: String,
    val displayName: String?,
    val username: String,
    val avatar: String?,
    val content: String,
    val followingCount: Int,
    val followerCount: Int,
    val attachmentMediaList: List<MediaBindingModel>
)