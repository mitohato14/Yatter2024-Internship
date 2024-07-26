package com.dmm.bootcamp.yatter2024.ui.timelinedetail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dmm.bootcamp.yatter2024.common.navigation.Destination

class TimelineDetailDestination(
    private val id: String,
) : Destination(ROUTE){

    override fun buildRoute(): String {
        return buildString {
            append(ROUTE_PATH)
            append("?${KEY_ID}=$id")
        }
    }

    companion object {
        private const val ROUTE_PATH = "timelineDetail"
        private const val KEY_ID = "id"
        private const val ROUTE = "${ROUTE_PATH}?$KEY_ID={$KEY_ID}"

        fun createNode(builder: NavGraphBuilder) {
            builder.composable(
                route = ROUTE,
                arguments = listOf(
                    navArgument(KEY_ID) {
                        type = NavType.StringType
                    },
                )
            ) { backStackEntry ->
                val id = requireNotNull(backStackEntry.arguments?.getString(KEY_ID))
                TimelineDetailPage(id)
            }
        }
    }
}