package com.dmm.bootcamp.yatter2024.ui.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.ui.login.LoginUiState
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineDestination
import com.dmm.bootcamp.yatter2024.usecase.register.RegisterAccountUseCase
import com.dmm.bootcamp.yatter2024.usecase.register.RegisterAccountUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterAccountUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState.empty())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()
    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    fun onChangeUsername(username: String){
        val snapshotBindingModel = uiState.value.registerBindingModel
        _uiState.update{
            it.copy(
                validUsername = Username(username).validate(),
                registerBindingModel = snapshotBindingModel.copy(
                    username = username
                )
            )
        }
    }

    fun onChangePassword(password: String){
        val snapshotBindingModel = uiState.value.registerBindingModel
        _uiState.update{
            it.copy(
                validPassword = Password(password).validate(),
                registerBindingModel = snapshotBindingModel.copy(
                    password = password
                )
            )
        }
    }

    fun onClickRegister(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true)}

            val snapBindingModel = uiState.value.registerBindingModel
            when (
                registerUseCase.execute(
                    snapBindingModel.username,
                    snapBindingModel.password,
                )
            ){
                is RegisterAccountUseCaseResult.Success -> {
                    _destination.value = PublicTimelineDestination{
                        popUpTo(RegisterDestination().route){
                            inclusive = true
                        }
                    }

                }

                is RegisterAccountUseCaseResult.Failure -> {
                    Log.ERROR
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onCompleteNavigation(){
        _destination.value = null
    }
 }