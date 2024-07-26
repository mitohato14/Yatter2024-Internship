package com.dmm.bootcamp.yatter2024.ui.timelinedetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dmm.bootcamp.yatter2024.R
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.StatusBindingModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TimelineDetailTemplate(
    statusBindingModel: StatusBindingModel,
    isLoading: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onClickNavIcon: () -> Unit,
    onClickAvatar: (String) -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "タイムライン")
                },
                navigationIcon = {
                    IconButton(onClick = onClickNavIcon) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "戻る"
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    val context = LocalContext.current

                    // プレイスホルダー画像の生成
                    val placeholder = ResourcesCompat.getDrawable(
                        context.resources,
                        R.drawable.avatar_placeholder,
                        null,
                    )

                    AsyncImage(
                        modifier = Modifier
                            .size(64.dp)
                            .clickable { onClickAvatar(statusBindingModel.id) },
                        // ImageRequestを作成して、画像取得できていない状態のプレイスホルダー設定
                        model = ImageRequest.Builder(context)
                            .data(statusBindingModel.avatar)
                            .placeholder(placeholder)
                            .error(placeholder)
                            .fallback(placeholder)
                            .setHeader("User-Agent", "Mozilla/5.0") // モックサーバーから画像取得する場合のみ追加
                            .build(),
                        contentDescription = "アバター画像",
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        if (statusBindingModel.displayName != "") {
                            Text(
                                text = statusBindingModel.displayName,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Text(
                                text = statusBindingModel.username,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(text = statusBindingModel.username)
                    }
                }
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = statusBindingModel.content
                )
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Preview
@Composable
private fun TimelineDetailTemplatePreview() {
    Yatter2024Theme {
        Surface() {
            TimelineDetailTemplate(
                statusBindingModel = StatusBindingModel(
                    id = "id1",
                    displayName = "display name1",
                    username = "username1",
                    avatar = null,
                    content = "preview content1",
                    attachmentMediaList = listOf()
                ),
                isLoading = true,
                isRefreshing = false,
                onRefresh = {},
                onClickNavIcon = {},
                onClickAvatar = {},
            )
        }
    }
}