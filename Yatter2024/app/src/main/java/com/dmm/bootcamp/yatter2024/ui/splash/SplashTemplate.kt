package com.dmm.bootcamp.yatter2024.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmm.bootcamp.yatter2024.R
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.LottieCompositionSpec
import kotlin.reflect.KMutableProperty0


@Composable
fun SplashTemplate(
) {
    Yatter2024Theme {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                val composition by rememberLottieComposition(
                    spec = LottieCompositionSpec.RawRes(R.raw.my_lottie) // ここにLottieファイルのリソースIDを指定します
                )
                LottieAnimation(
                    composition = composition,
                    modifier = Modifier.size(300.dp) // 必要に応じてアニメーションのサイズを調整
                )
            }
        }
    }
}

@Preview
@Composable
fun SplashTemplatePreview(){
    SplashTemplate(
    )
}