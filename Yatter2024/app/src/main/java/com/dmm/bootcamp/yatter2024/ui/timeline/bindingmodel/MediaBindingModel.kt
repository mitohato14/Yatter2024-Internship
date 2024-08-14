package com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel

data class MediaBindingModel(
    val id: String,
    val type: String,
    val url: String,
    val description: String?
)

data class StatusBindingModel(
    val id: String,
    val displayName: String,
    val username: String,
    val avatar: String?,
    val content: String,
    val attachmentMediaList: List<MediaBindingModel>
)


