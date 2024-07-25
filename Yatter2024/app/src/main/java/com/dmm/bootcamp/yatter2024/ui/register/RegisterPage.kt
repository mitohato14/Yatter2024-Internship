package com.dmm.bootcamp.yatter2024.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterPage(
    viewModel: RegisterViewModel = getViewModel(),
) {
    val destination by viewModel.destination.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    LaunchedEffect(destination)  {
        destination?.let {
            it.navigate(navController)
            viewModel.onCompleteNavigation()
        }
    }

    val uiState: RegisterUiState by viewModel.uiState.collectAsStateWithLifecycle()
    RegisterTemplate(
        userName = uiState.registerBindingModel.username,
        onChangedUserName = viewModel::onChangedUsername,
        password = uiState.registerBindingModel.password,
        onChangedPassword = viewModel::onChangedPassword,
        isEnableRegister = uiState.isEnableRegister,
        onClickRegister = viewModel::onClickRegister,
        isLoading = uiState.isLoading,
    )
}