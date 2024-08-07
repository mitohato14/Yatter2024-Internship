package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dmm.bootcamp.yatter2024.R
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.ProfileBindingModel
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.StatusBindingModel
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileTemplate(
    statusList: List<StatusBindingModel>,
    profileBindingModel: ProfileBindingModel,
    isLoading: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onClickPost: () -> Unit,
    onClickNavIcon: () -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "プロフィール")
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
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onClickPost) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "post"
                )
            }
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .padding(paddingValues),
        ) {
            Column {
                Box {

                }
                // ヘッダー画像がnullの場合にプレースホルダーを表示
                if (profileBindingModel.header != null) {
                    AsyncImage(
                        modifier = Modifier.fillMaxWidth(),
                        model = profileBindingModel.header,
                        contentDescription = "ヘッダー画像",
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(100.dp)
                            .background(Color.Gray)
                    )
                }

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
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    // ImageRequestを作成して、画像取得できていない状態のプレイスホルダー設定
                    model = ImageRequest.Builder(context)
                        .data(profileBindingModel.avatar)
                        .placeholder(placeholder)
                        .error(placeholder)
                        .fallback(placeholder)
                        .setHeader("User-Agent", "Mozilla/5.0") // モックサーバーから画像取得する場合のみ追加
                        .build(),
                    contentDescription = stringResource(id = R.string.public_timeline_avatar_content_description),
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp)

                ) {
                    if(profileBindingModel.displayName != ""){
                        Text(
                            text = profileBindingModel.displayName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }else{
                        Text(
                            text = profileBindingModel.username,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = " @${profileBindingModel.username}",
                        style = MaterialTheme.typography.body2.copy(
                            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium)
                        ),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = profileBindingModel.note,
                        style = MaterialTheme.typography.body2,
                    )
                    Row {
                        Text(
                            text = "${profileBindingModel.followingCount}フォロー中",
                            style = MaterialTheme.typography.body2,
                        )
                        Text(
                            text = " ${profileBindingModel.followerCount}フォロワー",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                ) {
                    items(statusList) { item ->
                        StatusRow(statusBindingModel = item)
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

}

@Preview
@Composable
private fun ProfileTemplatePreview() {
    Yatter2024Theme {
        Surface {
            ProfileTemplate(
                statusList = listOf(
                    StatusBindingModel(
                        id = "id1",
                        displayName = "display name1",
                        username = "username1",
                        avatar = null,
                        content = "preview content1",
                        attachmentMediaList = listOf()
                    ),
                    StatusBindingModel(
                        id = "id2",
                        displayName = "display name2",
                        username = "username2",
                        avatar = null,
                        content = "preview content2",
                        attachmentMediaList = listOf()
                    ),
                ),
                profileBindingModel = ProfileBindingModel(
                    id = "id1",
                    displayName = "display name1",
                    username = "username1",
                    avatar = null,
                    header = null,
                    note = "preview note",
                    followingCount = 10,
                    followerCount = 10,
                ),
                isLoading = true,
                isRefreshing = false,
                onRefresh = {},
                onClickPost = {},
                onClickNavIcon = {},
            )
        }
    }
}