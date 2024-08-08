package com.dmm.bootcamp.yatter2024.ui.post

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
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import org.koin.androidx.compose.getViewModel
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

@Composable
fun PostPage(
    viewModel: PostViewModel = getViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val destination by viewModel.destination.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    val context = LocalContext.current
    val callGetImage by viewModel.callGetImage.collectAsStateWithLifecycle()
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
                viewModel.onAttachedImage(file)
            }
        }
    }
    LaunchedEffect(destination) {
        destination?.navigate(navController)
        viewModel.onCompleteNavigation()
    }
    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE ) {
        viewModel.onCreate()
    }
    LaunchedEffect(callGetImage) {
        if(callGetImage != null){
            launcherHeader.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }
    PostTemplate(
        postBindingModel = uiState.bindingModel,
        isLoading = uiState.isLoading,
        canPost = uiState.canPost,
        onStatusTextChanged = viewModel::onChangedStatusText,
        onClickPost = viewModel::onClickPost,
        onClickNavIcon = viewModel::onClickNavIcon,
        getImage = viewModel::getImage,
        )
}