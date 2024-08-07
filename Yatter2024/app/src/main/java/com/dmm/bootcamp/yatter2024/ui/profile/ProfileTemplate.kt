package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dmm.bootcamp.yatter2024.R
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024ThemeProfile

@Composable
fun ProfileTemplate(
    userName: String,
    displayName: String,
    note: String,
    avatar: String,
    header: String,
    followingCount: Int,
    followerCount: Int,
    isMe : Boolean,
    onClickUpdate: () -> Unit
) {
    Yatter2024ThemeProfile {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "プロフィール")
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
                Column(modifier = Modifier.fillMaxSize()) {
                    // ヘッダー画像
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(header)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )

                    // アバター画像
                    val context = LocalContext.current
                    val placeholder = remember {
                        ResourcesCompat.getDrawable(
                            context.resources,
                            R.drawable.avatar_placeholder,
                            null
                        )
                    }

                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(avatar ?: R.drawable.avatar_placeholder)
                            .placeholder(placeholder)
                            .error(placeholder)
                            .fallback(placeholder)
                            .setHeader("User-Agent", "Mozilla/5.0") // モックサーバーから画像取得する場合のみ追加
                            .build(),
                        contentDescription = stringResource(id = R.string.public_timeline_avatar_content_description),
                        modifier = Modifier
                            .size(80.dp)
                            .padding(top = 16.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    // ユーザー名
                    Text(
                        text = "ユーザ名: $userName",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    // 表示名
                    Text(
                        text = "表示名: $displayName",
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    // メモ
                    Text(
                        text = "ノート",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = note,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    // フォロワー数とフォロー数
                    Row(
                        modifier = Modifier.padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Following: $followingCount",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(text = "Followers: $followerCount")
                    }
                    if (isMe) {
                        Button(
                            onClick = onClickUpdate,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "プロフィールの変更")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfileTemplate() {
    ProfileTemplate(
        userName = "ユーザー名",
        displayName = "表示名",
        note = "これはユーザーのメモです。",
        avatar = "",
        header = "https://pixabay.com/ja/photos/%E6%B0%B4-%E6%96%B0%E9%AE%AE%E3%81%AA-%E6%99%B4%E3%82%8C-%E5%A4%8F-%E6%BB%B4-2559064/",
        followingCount = 10,
        followerCount = 30,
        isMe = true,
        onClickUpdate = {}
    )
}