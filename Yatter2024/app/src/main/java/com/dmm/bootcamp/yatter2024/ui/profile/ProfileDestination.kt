package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.ui.login.LoginPage
import com.dmm.bootcamp.yatter2024.ui.post.PostPage

class ProfileDestination : Destination(ROUTE) {
    companion object {
        private const val ROUTE = "profile"

        fun createNode(builder: NavGraphBuilder) {
            builder.composable(ROUTE) {
                ProfilePage()
            }
        }
    }
}