package com.dmm.bootcamp.yatter2024.ui.edit

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.ui.profile.ProfilePage

class EditDestination : Destination(ROUTE) {
    companion object {
        private const val ROUTE = "edit"

        fun createNode(builder: NavGraphBuilder) {
            builder.composable(ROUTE) {
                EditPage()
            }
        }
    }
}