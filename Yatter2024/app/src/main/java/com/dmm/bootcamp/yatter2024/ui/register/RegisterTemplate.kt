package com.dmm.bootcamp.yatter2024.ui.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme

@Composable
fun RegisterTemplate(
    userName: String,
    onChangedUserName: (String) -> Unit,
    password: String,
    onChangedPassword: (String) -> Unit,
    isEnableRegister: Boolean,
    onClickRegister: () -> Unit,
    isLoading: Boolean,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "新規会員登録")
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    text = "ユーザー名"
                )
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    value = userName,
                    onValueChange = onChangedUserName,
                    placeholder = {
                        Text(text = "username")
                    },
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "パスワード"
                )
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = password,
                    onValueChange = onChangedPassword,
                    placeholder = {
                        Text(text = "password")
                    },
                )
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 16.dp),
                    text = "パスワードは英数字８文字以上、必ず大文字小文字記号を含むこと",
                    style = TextStyle(
                        fontSize = 12.sp, // フォントサイズを小さくする
                        color = Color.Gray // フォントカラーをグレーにする
                    )
                )
                Button(
                    enabled = isEnableRegister,
                    onClick = onClickRegister,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Text(text = "新規会員登録")
                }
            }
            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
private fun RegisterTemplatePreview() {
    Yatter2024Theme {
        Surface() {
            RegisterTemplate(
                userName = "username",
                onChangedUserName = {},
                password = "password",
                onChangedPassword = {},
                isEnableRegister = true,
                onClickRegister = {},
                isLoading = false,
            )
        }
    }
}