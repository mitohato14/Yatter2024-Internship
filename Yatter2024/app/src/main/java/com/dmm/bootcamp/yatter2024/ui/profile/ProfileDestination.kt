package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.navigation.NavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dmm.bootcamp.yatter2024.common.navigation.Destination

class ProfileDestination(
    builder: (NavOptionsBuilder.() -> Unit)? = null,
    val username: String = "",

) : Destination(ROUTE,builder) {
    companion object{
        private const val ROUTE = "profile?username={username}"

        fun createNode(builder: NavGraphBuilder){
            builder.composable(ROUTE,arguments = listOf(navArgument("username"){
                this.type = NavType.StringType
            })){
                ProfilePage(requireNotNull(it.arguments?.getString("username")))
            }
        }
    }
    override fun buildRoute():String {
        return this.route.replace("{username}",username)
    }
}