package com.dmm.bootcamp.yatter2024.ui.profile

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfilePage(
    userName: String,
    viewModel: ProfileViewModel = getViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Log.d("ProfilePage", "userName: $userName")

    val destination by viewModel.destination.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    LaunchedEffect(destination) {
        destination?.let {
            it.navigate(navController)
            viewModel.onCompleteNavigation()
        }
    }

    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
        viewModel.onCreate(userName)
        viewModel.onResume()
    }
    ProfileTemplate(
        userName = uiState.username,
        displayName = uiState.ProfileBindingModel.displayName ?: "",
        note = uiState.ProfileBindingModel.note ?: "",
        avatar = uiState.ProfileBindingModel.avatar ?: "",
        header = uiState.ProfileBindingModel.header ?: "",
        followingCount = uiState.ProfileBindingModel.followingCount ?: 0,
        followerCount = uiState.ProfileBindingModel.followerCount ?: 0,
        isMe = uiState.isMe,
        onClickUpdate = viewModel::onClickUpdate,
    )
}