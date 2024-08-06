package com.dmm.bootcamp.yatter2024.ui.update.bindingmodel

import java.net.URL

data class ProfileUpdateBindingModel(
    val displayName: String?,
    val note: String?,
    val avatar: URL?,
    val header: URL?,
)
