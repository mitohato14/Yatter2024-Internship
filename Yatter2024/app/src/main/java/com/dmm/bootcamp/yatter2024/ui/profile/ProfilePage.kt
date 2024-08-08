package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProfilePage (
    username: String,
    viewModel: ProfileViewModel = getViewModel{ parametersOf(username)},
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val destination by viewModel.destination.collectAsStateWithLifecycle()
    val navController = LocalNavController.current

    LaunchedEffect(destination){
        destination?.let{
            it.navigate(navController)
            viewModel.onCompleteNavigation()
        }
    }
    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
        viewModel.onResume()
    }
    ProfileTemplate(
        profileBindingModel = uiState.profileBindingModel,
        statusList = uiState.statusList,
        isLoading = uiState.isLoading,
        isRefreshing = uiState.isRefreshing,
        onRefresh = viewModel::onRefresh,
        onClickPost = viewModel::onClickPost,
        onClickLogout = viewModel::onClickLogout,
        onClickFollow = viewModel::onClickFollow,
        onClickFollower = viewModel::onClickFollower,
        onCliCkSetting = viewModel::onClickSetting
    )
}