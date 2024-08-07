package com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel

data class StatusBindingModel(
    val id: String,
    val displayName: String,
    val username: String,
    val avatar: String?,
    val content: String,
    val attachmentMediaList: List<MediaBindingModel>
)
