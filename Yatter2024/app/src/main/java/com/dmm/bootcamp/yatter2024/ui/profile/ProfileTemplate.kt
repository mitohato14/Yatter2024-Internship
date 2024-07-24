package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmm.bootcamp.yatter2024.ui.login.LoginTemplate
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileBindingModel
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme
import com.dmm.bootcamp.yatter2024.ui.timeline.StatusRow
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.StatusBindingModel

@Composable
fun ProfileTemplate(
    //publictimeline参考にする？
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "プロフィール")
                }
            )
        },
        //ここにパブリックタイムラインに作ったボトムバーを持ってくる
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                //ひとまずデフォルトのアイコン入れちゃったのでユーザのアイコンに変える
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "default",
                    modifier = Modifier.size(120.dp)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    text = "ユーザー名",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    text = "フォロー数",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                )
                Text(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    text = "フォロワー数",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                )

                //ページ下部に
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                ) {
                    //ここで自分のタイムラインの表示
                }

            }
        }
    }
}

@Preview
@Composable
private fun LoginTemplatePreview() {
    Yatter2024Theme {
        Surface() {
            ProfileTemplate(
                //publictimeline参考にする？
            )
        }
    }
}