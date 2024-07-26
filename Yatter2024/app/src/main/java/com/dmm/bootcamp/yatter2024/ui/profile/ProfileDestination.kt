package com.dmm.bootcamp.yatter2024.ui.profile

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dmm.bootcamp.yatter2024.common.navigation.Destination

class ProfileDestination : Destination(ROUTE) {
    companion object {
        private var ROUTE = "profile/{userName}"

        fun createRoute(userName: String){
            ROUTE = "profile/$userName"
        }

        fun createNode(builder: NavGraphBuilder) {
            builder.composable(
                ROUTE,
                arguments = listOf(navArgument("userName") { type = NavType.StringType })
            ) { backStackEntry ->
                Log.d("ProfileDestination", "ROUTE: $ROUTE")
                Log.d("ProfileDestination", "arguments: $backStackEntry.arguments")
                val userName = backStackEntry.arguments?.getString("userName") ?: return@composable
                ProfilePage(userName)
            }
        }
    }
}