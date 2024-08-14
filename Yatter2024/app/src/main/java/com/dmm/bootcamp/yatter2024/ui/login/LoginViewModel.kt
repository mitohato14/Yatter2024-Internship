package com.dmm.bootcamp.yatter2024.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.ui.signup.SignUpDestination
import com.dmm.bootcamp.yatter2024.ui.signup.SignUpUiState
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineDestination
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCaseResult
import com.dmm.bootcamp.yatter2024.usecase.register.RegisterAccountUseCase
import com.dmm.bootcamp.yatter2024.usecase.register.RegisterAccountUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.empty())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onChangedUsername(username: String) { //バリデーションチェック結果と新しいusernameを更新
        val snapshotBindingModel = uiState.value.loginBindingModel
        _uiState.update{
            it.copy(
                validUsername = Username(username).validate(),
                loginBindingModel = snapshotBindingModel.copy(
                    username = username
                )
            )
        }
    }

    fun onChangedPassword(password: String) {
        val snapshotBindingModel = uiState.value.loginBindingModel
        _uiState.update {
            it.copy(
                validPassword = Password(password).validate(),
                loginBindingModel = snapshotBindingModel.copy(
                    password = password
                )
            )
        }
    }

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    fun onClickLogin() { //ログインボタンが押された時の処理
        viewModelScope.launch{
            _uiState.update { it.copy(isLoading = true) }

            val snapBindingModel = uiState.value.loginBindingModel
            when(
                val result =
                    loginUseCase.execute(
                        Username(snapBindingModel.username),
                        Password(snapBindingModel.password),
                    )
            ){
                is LoginUseCaseResult.Success -> {
                    _destination.value = PublicTimelineDestination()
                }

                is LoginUseCaseResult.Failure -> {
                    Log.ERROR
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onClickRegister() { //新規登録ボタンが押された時の処理
        _destination.value = SignUpDestination()
    }

    fun onCompleteNavigation() {
        _destination.value = null
    }
}