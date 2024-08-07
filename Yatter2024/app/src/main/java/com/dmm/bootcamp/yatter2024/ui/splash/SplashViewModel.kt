package com.dmm.bootcamp.yatter2024.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val _isSplashFinished = MutableStateFlow(false)
    val isSplashFinished: StateFlow<Boolean> = _isSplashFinished.asStateFlow()

    init {
        viewModelScope.launch {
            delay(6000) // スプラッシュ画面の表示時間を設定 (例: 6秒)
            _isSplashFinished.value = true
        }
    }
}