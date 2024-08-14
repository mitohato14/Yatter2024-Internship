package com.dmm.bootcamp.yatter2024.ui.signup

import android.util.Log
import androidx.compose.animation.core.snap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.ui.login.LoginDestination
import com.dmm.bootcamp.yatter2024.ui.login.LoginPage
import com.dmm.bootcamp.yatter2024.ui.login.LoginUiState
import com.dmm.bootcamp.yatter2024.ui.login.LoginViewModel
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineDestination
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCaseResult
import com.dmm.bootcamp.yatter2024.usecase.register.RegisterAccountUseCase
import com.dmm.bootcamp.yatter2024.usecase.register.RegisterAccountUseCaseResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.userAgent

class SignUpViewModel(
    private val registerAccount: RegisterAccountUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState.empty())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun onChangedUsername(username: String) {
        val snapshotBindingModel = uiState.value.signUpBindingModel
        _uiState.update { it.copy(
            validUsername = Username(username).validate(), //同じユーザー名のアカウントがないかどうかを調べたい
            signUpBindingModel = snapshotBindingModel.copy(
                username = username
            )
        ) }
    }

    fun onChangedPassword(password: String) {
        val snapshotBindingModel = uiState.value.signUpBindingModel
        _uiState.update { it.copy(
            signUpBindingModel = snapshotBindingModel.copy(
                password = password
            )
        ) }
    }

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    fun onClickLogin(){
        _destination.value = LoginDestination()
    }

    fun onClickRegister() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) } // 1

            val snapBindingModel = uiState.value.signUpBindingModel
            when (
                val result =
                    registerAccount.execute(
                        username = snapBindingModel.username,
                        password = snapBindingModel.password
                    ) // 2
            ) {
                is RegisterAccountUseCaseResult.Success -> {
                    // 3
                    // パブリックタイムライン画面に遷移する処理の追加
                    _destination.value = PublicTimelineDestination()
                }

                is RegisterAccountUseCaseResult.Failure -> {
                    // エラー表示
                    Log.ERROR
                }
            }

            _uiState.update { it.copy(isLoading = false) } // 5
        }
    }

    fun onCompleteNavigation(){
        _destination.value = null
    }

}