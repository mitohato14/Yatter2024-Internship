package com.dmm.bootcamp.yatter2024

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dmm.bootcamp.yatter2024.ui.MainApp
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)

    setContent {
      Yatter2024Theme {
        Surface {
          MainApp()
        }
      }
    }
  }
}
