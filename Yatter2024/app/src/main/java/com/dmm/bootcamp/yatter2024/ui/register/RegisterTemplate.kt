package com.dmm.bootcamp.yatter2024.ui.register

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RegisterTemplate(
    userName: String,
    onChangedUsername: (String) -> Unit,
    password: String,
    onChangedPassword: (String) -> Unit,
    isLoading: Boolean,
    onClickRegister: () -> Unit,
    onClickLogin: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "アカウントを作成")
                },
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(10.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    value = userName,
                    onValueChange = onChangedUsername,
                    label = {
                        Text(text = "名前")
                    },
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    value = password,
                    onValueChange = onChangedPassword,
                    label = {
                        Text(text = "パスワード")
                    },
                )

                Button(
                    onClick = onClickRegister,
                    modifier = Modifier
                        .fillMaxWidth(),
                    ) {
                    Text(text = "新規登録")

                }

                Divider(modifier = Modifier.padding(vertical = 50.dp))

                Text(
                    text = "アカウントをお持ちの場合",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                TextButton(
                    onClick = onClickLogin,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Text(text = "ログイン")
                }

                if (isLoading){
                    CircularProgressIndicator()
                }
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
                userName= "田中太郎",
                onChangedUsername = {},
                password = "njuv4h4g56g",
                onChangedPassword = {},
                isLoading = false,
                onClickRegister = {},
                onClickLogin = {}
            )
        }
    }
}