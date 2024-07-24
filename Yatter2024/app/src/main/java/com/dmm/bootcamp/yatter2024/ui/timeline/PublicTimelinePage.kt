package com.dmm.bootcamp.yatter2024.ui.timeline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.getViewModel

@Composable
fun PublicTimelinePage(
    viewModel: PublicTimelineViewModel = getViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
        viewModel.onResume()
    }

    PublicTimelineTemplate(
        statusList = uiState.statusList,
        isLoading = uiState.isLoading,
        isRefreshing = uiState.isRefreshing,
        onRefresh = viewModel::onRefresh,
        )
}