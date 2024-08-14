package com.dmm.bootcamp.yatter2024.ui.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import com.dmm.bootcamp.yatter2024.ui.login.LoginTemplate
import com.dmm.bootcamp.yatter2024.ui.login.LoginUiState
import com.dmm.bootcamp.yatter2024.ui.login.LoginViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun SignUpPage(
    viewModel: SignUpViewModel = getViewModel(),
) {
    val uiState: SignUpUiState by viewModel.uiState.collectAsStateWithLifecycle()
    SignUpTemplate(
        userName = uiState.signUpBindingModel.username,
        onChangedUserName = viewModel::onChangedUsername,
        password = uiState.signUpBindingModel.password,
        onChangedPassword = viewModel::onChangedPassword,
        isEnableSignUp = uiState.isEnableSignUp,
        isLoading = uiState.isLoading,
        onClickLogin = viewModel::onClickLogin,
        onClickRegister = viewModel::onClickRegister,
    )

    val destination by viewModel.destination.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    LaunchedEffect(destination)  {
        destination?.let {
            it.navigate(navController)
            viewModel.onCompleteNavigation()
        }
    }
}