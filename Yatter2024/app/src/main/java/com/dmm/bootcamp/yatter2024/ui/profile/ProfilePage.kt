package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileTemplate
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileViewModel
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineTemplate
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfilePage(
    viewModel: ProfileViewModel = getViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val destination by viewModel.destination.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    LaunchedEffect(destination) {
        destination?.navigate(navController)
        viewModel.onCompleteNavigation()
    }

    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
        viewModel.onResume()
    }

    ProfileTemplate(
        statusList = uiState.statusList,
        isLoading = uiState.isLoading,
        isRefreshing = uiState.isRefreshing,
        onRefresh = viewModel::onRefresh,
    )
}