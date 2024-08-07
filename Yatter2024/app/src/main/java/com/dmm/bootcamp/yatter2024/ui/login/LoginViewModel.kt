package com.dmm.bootcamp.yatter2024.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.ui.register.RegisterDestination
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineDestination
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCaseResult
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

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()


    //テキストボックスにユーザーが文字を入力したときに、UiState内の値を入力された文字に更新する
    fun onChangedUsername(username: String) {
        val snapshotBindingModel = uiState.value.loginBindingModel
        _uiState.update {
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

    //ログインボタンをユーザーが押下したときに呼び出すメソッド。ログイン処理を実装。
    fun onClickLogin() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) } // 1

            val snapBindingModel = uiState.value.loginBindingModel
            when (
                val result =
                    loginUseCase.execute(
                        Username(snapBindingModel.username),
                        Password(snapBindingModel.password),
                    ) // 2
            ) {
                is LoginUseCaseResult.Success -> {
                    // 3
                    // パブリックタイムライン画面に遷移する処理の追加
                    _destination.value = PublicTimelineDestination{
                        popUpTo(LoginDestination().route) {
                            inclusive = true
                        }
                    }
                }

                is LoginUseCaseResult.Failure -> {
                    // 4
                    // エラー表示
                }

            }

            _uiState.update { it.copy(isLoading = false) } // 5
        }
    }

    //会員登録へ進むためのボタンをユーザーが押下したときに呼び出すメソッド。会員登録ボタンは会員登録画面への遷移を実装。
    fun onClickToRegister() {
        _destination.value = RegisterDestination()
    }

    fun onCompleteNavigation() {
        _destination.value = null
    }
}