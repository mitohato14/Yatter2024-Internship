package com.dmm.bootcamp.yatter2024.ui.post

import com.dmm.bootcamp.yatter2024.domain.model.MediaId

data class PostBindingModel(
    val avatarUrl: String?,
    val statusText: String,
    val attachmentList: List<String>,
    val mediaIdList: List<MediaId>
)
