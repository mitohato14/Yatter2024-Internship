package com.dmm.bootcamp.yatter2024.ui.setting

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2024.common.navigation.Destination

class SettingDestination(
    builder: (NavOptionsBuilder.() -> Unit)? = null,
) : Destination(ROUTE,builder){
    companion object{
        private const val ROUTE = "setting"
        fun createNode(builder: NavGraphBuilder){
            builder.composable(ROUTE){
                SettingPage()
            }
        }
    }
}