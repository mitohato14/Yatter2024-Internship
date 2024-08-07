package com.dmm.bootcamp.yatter2024

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen
import com.dmm.bootcamp.yatter2024.ui.MainApp
import com.dmm.bootcamp.yatter2024.ui.splash.SplashPage
import com.dmm.bootcamp.yatter2024.ui.splash.SplashTemplate
import com.dmm.bootcamp.yatter2024.ui.splash.SplashViewModel
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme
import org.koin.androidx.compose.getViewModel

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Yatter2024Theme {
        Surface {
          //スプラッシュ画面を表示してからmainappに遷移するように
          val splashViewModel: SplashViewModel = getViewModel()
          val isSplashFinished by splashViewModel.isSplashFinished.collectAsState()

          if (isSplashFinished) {
            MainApp()
          } else {
            SplashPage()
          }
        }
      }
    }
  }
}

