package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.StatusBindingModel

@Composable
fun ProfileTemplate(
    accountBindingModel: AccountBindingModel?,
    statusList: List<StatusBindingModel>,
    isLoading: Boolean,
    onClickNavIcon: () -> Unit
) {

}

@Preview
@Composable
private fun ProfileTemplatePreview() {
    Yatter2024Theme {
        Surface() {
            ProfileTemplate(
                accountBindingModel = AccountBindingModel(
                    username = "name",
                    displayName = "null",
                    note = "note",
                    avatar = null,
                    header = null,
                    followingCount = 100,
                    followerCount = 5000,
                ),
                statusList = listOf(),
                isLoading = false,
                onClickNavIcon = {}
            )

        }
    }
}
