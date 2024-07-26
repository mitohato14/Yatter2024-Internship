package com.dmm.bootcamp.yatter2024.ui.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileTemplate
import org.koin.androidx.compose.getViewModel

@Composable
fun EditPage(
    viewModel: EditViewModel = getViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val destination by viewModel.destination.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    LaunchedEffect(destination) {
        destination?.navigate(navController)
        viewModel.onCompleteNavigation()
    }

    EditTemplate(
        isLoading = uiState.isLoading,
        userName = uiState.editBindingModel.username,
        password = uiState.editBindingModel.password,
        onChangedUserName = viewModel::onChangedUsername,
        onChangedPassword = viewModel::onChangedPassword,
        onClickProfile = viewModel::onClickProfile,
        onClickHome = viewModel::onClickHome,
    )
}