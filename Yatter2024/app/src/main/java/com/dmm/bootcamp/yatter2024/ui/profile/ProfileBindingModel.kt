package com.dmm.bootcamp.yatter2024.ui.profile

import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.MediaBindingModel


data class ProfileBindingModel(
    val id: String,
    val displayName: String,
    val username: String,
    val avatar: String?,
    val content: String,
    val attachmentMediaList: List<MediaBindingModel>
)