package com.dmm.bootcamp.yatter2024.ui.setting

import com.dmm.bootcamp.yatter2024.domain.model.Me

data class SettingUiState(
    val settingBindingModel: SettingBindingModel,
    val isLoading: Boolean,
) {
    companion object {
        fun empty(): SettingUiState = SettingUiState(
            settingBindingModel = SettingBindingModel(
                me = null,
                newDisplayName = null,
                newNote = null,
                newAvatar = null,
                newHeader = null,
            ),
            isLoading = false,
        )
    }
}