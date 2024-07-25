package com.dmm.bootcamp.yatter2024.ui.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileBindingModel
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileTemplate
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.StatusBindingModel
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme

@Composable
fun SettingTemplate(
    settingBindingModel: SettingBindingModel,
    isLoading: Boolean,
    onChangedDisplayNameText: (String) -> Unit,
    onChangedNoteText: (String) -> Unit,
    onChangedAvatarText: () -> Unit,
    onChangedHeaderText: () -> Unit,
    onClickChanged: () -> Unit,
    onClickBackIcon: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Setting")
                },
                navigationIcon = {
                    IconButton(onClick = onClickBackIcon){
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "back"
                        )
                    }
                }
            )
        },
    ) {
        val context = LocalContext.current
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ){
            Column(
                modifier = Modifier.fillMaxWidth(),
            ){
                Box(
                    modifier = Modifier.fillMaxWidth().height(80.dp).clickable(onClick = onChangedHeaderText)
                ){
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = ImageRequest.Builder(context)
                            .data(settingBindingModel.me?.header)
                            .setHeader("User-Agent","Mozilla/5.0")
                            .build(),
                        contentDescription = "Image of header",
                        contentScale = ContentScale.Crop,
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                    )
                    Text(text = "Change the header")
                }

                Row{
                    Box(
                        modifier = Modifier.size(48.dp).clickable(onClick = onChangedAvatarText)
                    ){
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = ImageRequest.Builder(context)
                                .data(settingBindingModel.me?.avatar)
                                .setHeader("User-Agent","Mozilla/5.0")
                                .build(),
                            contentDescription = "Image of avatar",
                            contentScale = ContentScale.Crop,
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.5f))
                        )
                        Text(text = "Change the avatar")
                    }
                    Column{
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            value = settingBindingModel.newDisplayName ?: "",
                            onValueChange = onChangedDisplayNameText,
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            ),
                        )

                    }
                    Button(onClick = onClickChanged) {
                        Text(text = "Complete")
                    }
                }
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(5f),
                    value = settingBindingModel.newNote ?: "",
                    onValueChange = onChangedNoteText,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                )
            }
            if (isLoading){
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
private fun SettingTemplatePreview() {
    Yatter2024Theme {
        Surface {
            SettingTemplate(
                settingBindingModel = SettingBindingModel(
                    me = null,
                    newDisplayName = "aaaa",
                    newNote = "Hello world",
                    newAvatar = "https://appstars.jp/wp-content/uploads/2020/05/egg_step_1.png",
                    newHeader = "https://cottoitalia.com/wp-content/uploads/2019/12/grey-brush-2.jpg",
                ),
                isLoading = false,
                onClickChanged = {},
                onChangedAvatarText = {},
                onChangedNoteText = {},
                onChangedHeaderText = {},
                onClickBackIcon = {},
                onChangedDisplayNameText = {},
            )
        }
    }
}