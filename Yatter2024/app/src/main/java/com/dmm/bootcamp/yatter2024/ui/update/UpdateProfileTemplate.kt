package com.dmm.bootcamp.yatter2024.ui.update

import android.util.Log
import android.webkit.URLUtil.isValidUrl
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.dmm.bootcamp.yatter2024.R
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024ThemeProfile
import java.net.URL

@Composable
fun ProfileUpdateTemplate(
    userName: String,
    displayName: String,
    note: String,
    avatar: String,
    header: String,
    onChangedDisplayName: (String) -> Unit,
    onChangedNote: (String) -> Unit,
    onChangedAvatar: (URL) -> Unit,
    onChangedHeader: (URL) -> Unit,
    isLoading: Boolean,
    onClickProfileUpdate: () -> Unit,
) {
    Log.d("ProfileUpdateTemplate", "userName: $displayName")
    Log.d("ProfileUpdateTemplate", "displayName: $displayName")

    Yatter2024ThemeProfile {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "$userName のプロフィール")
                    }
                )
            },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Update Button
                    Button(
                        enabled = true,
                        onClick = onClickProfileUpdate,
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Text(text = "更新")
                    }

                    // Display Name
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        text = "表示名"
                    )
                    OutlinedTextField(
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        value = displayName,
                        onValueChange = onChangedDisplayName,
                        placeholder = {
                            Text(text = displayName)
                        },
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        text = "ノート"
                    )
                    OutlinedTextField(
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        value = note,
                        onValueChange = onChangedNote,
                        placeholder = {
                            Text(text = note)
                        },
                    )

                    // Avatar URL
                    OutlinedTextField(
                        value = avatar,
                        onValueChange = {
                            if (isValidUrl(it)) {
                                onChangedAvatar(URL(it))
                            }
                        },
                        label = { Text("Avatar URL") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (avatar.isNotEmpty()) {
                        val painter = rememberImagePainter(
                            data = avatar,
                            builder = {
                                placeholder(R.drawable.avatar_placeholder)
                                error(R.drawable.avatar_placeholder)
                            }
                        )
                        Image(
                            painter = painter,
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = header,
                        onValueChange = {
                            if (isValidUrl(it)) {
                                onChangedHeader(URL(it))
                            }
                        },
                        label = { Text("Header URL") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (header.isNotEmpty()) {
                        val painter = rememberImagePainter(
                            data = header,
                            builder = {
                                placeholder(R.drawable.avatar_placeholder)
                                error(R.drawable.avatar_placeholder)
                            }
                        )
                        Image(
                            painter = painter,
                            contentDescription = "Header",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileUpdateTemplatePreview() {
    ProfileUpdateTemplate(
        userName = "john_doe",
        displayName = "John Doe",
        note = "Hello, I'm John!",
        avatar = "https://example.com/avatar.jpg",
        header = "https://example.com/header.jpg",
        onChangedDisplayName = {},
        onChangedNote = {},
        onChangedAvatar = {},
        onChangedHeader = {},
        isLoading = true,
        onClickProfileUpdate = {}
    )
}
