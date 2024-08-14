package com.dmm.bootcamp.yatter2024.ui.timeline

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.ui.login.LoginPage

class LogoutDestination : Destination(ROUTE){
    companion object{
        private const val ROUTE = "login"

        fun createNode(builder: NavGraphBuilder){
            builder.composable(ROUTE){
                LoginPage()
            }
        }
    }
}