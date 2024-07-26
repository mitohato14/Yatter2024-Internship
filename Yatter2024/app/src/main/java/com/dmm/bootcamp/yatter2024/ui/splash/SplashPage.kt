package com.dmm.bootcamp.yatter2024.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashPage(
    viewModel: SplashViewModel = getViewModel()
) {
    SplashTemplate()
    // スプラッシュ画面の表示時間が終了した後の処理は `MainActivity` 内で制御されるため、ここでは `SplashTemplate` のみ表示
}