package com.dmm.bootcamp.yatter2024.ui.setting

import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2024.MainActivity
import com.dmm.bootcamp.yatter2024.common.navigation.PopBackDestination.navigate
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import org.koin.androidx.compose.getViewModel
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

@Composable
fun SettingPage (
    viewModel: SettingViewModel = getViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val destination by viewModel.destination.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    val callGetImageHeader by viewModel.callGetImageHeader.collectAsStateWithLifecycle()
    val callGetImageAvatar by viewModel.callGetImageAvatar.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val launcherAvatar = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            context.contentResolver.openInputStream(uri)?.use{ inputStream ->
                val dir = File(context.filesDir,"images")
                if(!dir.exists()){
                    dir.mkdir()
                }
                val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(
                    context.contentResolver.getType(uri)
                )
                val file = File(dir, UUID.randomUUID().toString() + "."+extension)
                FileOutputStream(file).use{
                    var read = 0
                    val buffer = ByteArray(8192)
                    do {
                        read = inputStream.read(buffer, 0, 8192)
                        if (read != -1){
                            it.write(buffer, 0, read)
                        }
                    }while(read != -1)
                }
                viewModel.onChangedAvatarText(file)
            }
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }
    val launcherHeader = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val dir = File(context.filesDir, "images")
                if (!dir.exists()) {
                    dir.mkdir()
                }
                val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(
                    context.contentResolver.getType(uri)
                )
                val file = File(dir, UUID.randomUUID().toString() + "." + extension)
                FileOutputStream(file).use {
                    var read = 0
                    val buffer = ByteArray(8192)
                    do {
                        read = inputStream.read(buffer, 0, 8192)
                        if (read != -1) {
                            it.write(buffer, 0, read)
                        }
                    } while (read != -1)
                }
                viewModel.onChangedHeaderText(file)
            }
        }
    }
    LaunchedEffect(destination){
        destination?.navigate(navController)
        viewModel.onCompleteNavigation()
    }
    LaunchedEffect(callGetImageHeader){
        if(callGetImageHeader != null){
            launcherHeader.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

    }
    LaunchedEffect(callGetImageAvatar){
        if(callGetImageAvatar != null) {
            launcherAvatar.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }
    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
        viewModel.onCreate()
    }
    SettingTemplate(
        settingBindingModel = uiState.settingBindingModel,
        isLoading = uiState.isLoading,
        onChangedDisplayNameText = viewModel::onChangedDisplayNameText,
        onChangedNoteText = viewModel::onChangedNoteText,
        onChangedAvatarText = viewModel::getImageAvatar,
        onChangedHeaderText = viewModel::getImageHeader,
        onClickChanged = viewModel::onClickChanged,
        onClickBackIcon = viewModel::onClickBackIcon,

        )
}