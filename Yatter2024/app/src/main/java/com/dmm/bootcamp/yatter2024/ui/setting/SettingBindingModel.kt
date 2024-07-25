package com.dmm.bootcamp.yatter2024.ui.setting

import android.view.Display
import com.dmm.bootcamp.yatter2024.domain.model.Me
import java.net.URL

data class SettingBindingModel (
    val me: Me?,
    val newDisplayName: String?,
    val newNote: String?,
    val newAvatar: String?,
    val newHeader: String?,
)