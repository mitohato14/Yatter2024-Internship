package com.dmm.bootcamp.yatter2024.ui.timelinedetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2024.domain.model.StatusId
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import com.dmm.bootcamp.yatter2024.ui.post.PostTemplate
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun TimelineDetailPage(
    id: String,
    viewModel: TimelineDetailViewModel = getViewModel{ parametersOf(StatusId(id)) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val destination by viewModel.destination.collectAsStateWithLifecycle()
    val navController = LocalNavController.current

    LaunchedEffect(destination) {
        destination?.let {
            it.navigate(navController)
            viewModel.onCompleteNavigation()
        }
    }

    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
        viewModel.onResume()
    }

    TimelineDetailTemplate(
        statusBindingModel = uiState.statusBindingModel,
        isLoading = uiState.isLoading,
        isRefreshing = uiState.isRefreshing,
        onRefresh = viewModel::onRefresh,
        onClickNavIcon = viewModel::onClickNavIcon,
        onClickAvatar = viewModel::onClickAvatar,
    )
}