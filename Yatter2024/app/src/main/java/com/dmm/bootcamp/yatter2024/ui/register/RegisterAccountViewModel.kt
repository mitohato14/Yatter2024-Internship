package com.dmm.bootcamp.yatter2024.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.ui.login.LoginDestination
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineDestination
import com.dmm.bootcamp.yatter2024.usecase.register.RegisterAccountUseCase
import com.dmm.bootcamp.yatter2024.usecase.register.RegisterAccountUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterAccountViewModel (
    private val registerAccountUseCase: RegisterAccountUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState.empty())
    val uiState: StateFlow<RegisterUiState> = _uiState

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    fun onClickRegister(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val snapBindingModel = uiState.value.bindingModel

            when (val result = registerAccountUseCase.execute(snapBindingModel.username, snapBindingModel.password)) {
                is RegisterAccountUseCaseResult.Success -> {
                    _destination.value = PublicTimelineDestination{
                        popUpTo(LoginDestination().route){
                            inclusive = true
                        }
                    }
                }
                is RegisterAccountUseCaseResult.Failure -> {
                    println(result)
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onChangedUsername(username: String){
        val snapBindingModel = uiState.value.bindingModel
        _uiState.update { it.copy(bindingModel = snapBindingModel.copy(username = username))}
    }

    fun onChangedPassword(password: String) {
        val snapBindingModel: RegisterBindingModel = uiState.value.bindingModel
        _uiState.update { it.copy(bindingModel = snapBindingModel.copy(password = password)) }
    }

    fun onCompleteNavigation() {
        _destination.value = null
    }

    fun onClickLogin(){
        _destination.value = LoginDestination()
    }

}