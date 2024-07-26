package com.dmm.bootcamp.yatter2024.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterPage(
    viewModel: RegisterAccountViewModel = getViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val destination by viewModel.destination.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    LaunchedEffect(destination) {
        destination?.navigate(navController)
        viewModel.onCompleteNavigation()
    }
    RegisterTemplate(
        userName = uiState.bindingModel.username,
        onChangedUsername = viewModel::onChangedUsername,
        password = uiState.bindingModel.password,
        onChangedPassword = viewModel::onChangedPassword,
        isLoading = uiState.isLoading,
        onClickRegister = viewModel::onClickRegister,
        onClickLogin = viewModel::onClickLogin,
    )
}