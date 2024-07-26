package com.dmm.bootcamp.yatter2024.ui.edit

import com.dmm.bootcamp.yatter2024.ui.edit.bindingmodel.EditBindingModel

data class EditUiState(
    val editBindingModel: EditBindingModel,
    val isLoading: Boolean,
    val validUsername: Boolean,
    val validPassword: Boolean,
){
    val isEnableEdit: Boolean = validUsername && validPassword
    companion object {
        fun empty(): EditUiState = EditUiState(
            editBindingModel = EditBindingModel(
                username = "",
                password = ""
            ),
            validUsername = false,
            validPassword = false,
            isLoading = false,
        )
    }
}