package com.dmm.bootcamp.yatter2024

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import com.dmm.bootcamp.yatter2024.ui.MainApp
import com.dmm.bootcamp.yatter2024.ui.login.LoginPage
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelinePage

class MainActivity : AppCompatActivity() {
//  fun getImage(onResultImage: (Uri) -> Unit) {
//    // https://developer.android.com/training/data-storage/shared/photopicker?hl=ja
//    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
//      // Callback is invoked after the user selects a media item or closes the
//      // photo picker.
//      if (uri != null) {
//        onResultImage(uri)
//      } else {
//        Log.d("PhotoPicker", "No media selected")
//      }
//    }
//    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      Yatter2024Theme {
        MainApp()
      }
    }
  }
}
