package com.dmm.bootcamp.yatter2024.ui.update

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2024.common.navigation.Destination

class UpdateProfileDestination (
    builder: (NavOptionsBuilder.() -> Unit)? = null,
    ) : Destination(ROUTE, builder)
    {
        companion object {
        private const val ROUTE = "profileUpdate"
        fun createNode(builder: NavGraphBuilder) {
            builder.composable(ROUTE) {
                ProfileUpdatePage()
            }
        }
    }
}