package com.dmm.bootcamp.yatter2024.ui.signup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.ui.login.LoginPage

class SignUpDestination : Destination(ROUTE){
    companion object{
        private const val ROUTE = "signup"

        fun createNode(builder: NavGraphBuilder){
            builder.composable(ROUTE){
                SignUpPage()
            }
        }
    }
}