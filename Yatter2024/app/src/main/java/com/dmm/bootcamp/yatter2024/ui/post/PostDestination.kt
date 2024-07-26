package com.dmm.bootcamp.yatter2024.di

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.ui.post.PostPage

class PostDestination : Destination(ROUTE) {
    companion object {
        private const val ROUTE = "post"

        fun createNode(builder: NavGraphBuilder) {
            builder.composable(ROUTE) {
                PostPage()
            }
        }
    }
}