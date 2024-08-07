package com.dmm.bootcamp.yatter2024.ui.update

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileUpdatePage(
    viewModel: UpdateProfileViewModel = getViewModel(),
) {
    val uiState: UpdateProfileUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isInitialized by viewModel.isInitialized.collectAsStateWithLifecycle()

    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
        viewModel.onCreate()
    }

    if (isInitialized) {
        ProfileUpdateTemplate(
            userName = uiState.username,
            displayName = uiState.profileUpdateBindingModel.displayName ?: "",
            note = uiState.profileUpdateBindingModel.note ?: "",
            avatar = uiState.profileUpdateBindingModel.avatar.toString() ?: "",
            header = uiState.profileUpdateBindingModel.header.toString() ?: "",
            onChangedDisplayName = viewModel::onChangedDisplayName,
            onChangedNote = viewModel::onChangedNote,
            onChangedAvatar = viewModel::onChangedAvatar,
            onChangedHeader = viewModel::onChangedHeader,
            isLoading = uiState.isLoading,
            onClickProfileUpdate = viewModel::onClickProfileUpdate
        )
    }
    val destination by viewModel.destination.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    LaunchedEffect(destination) {
        destination?.let {
            it.navigate(navController)
            viewModel.onCompleteNavigation()
        }
    }
}