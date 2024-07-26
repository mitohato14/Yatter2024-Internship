package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dmm.bootcamp.yatter2024.R
import com.dmm.bootcamp.yatter2024.domain.model.Me
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.MediaBindingModel
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.StatusBindingModel
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme
import java.net.URL


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileTemplate(
    statusBindingModel: StatusBindingModel,
    statusList: List<StatusBindingModel>,
    isLoading: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onClickProfile: () -> Unit,
    onClickHome: () -> Unit,
    onClickEdit: () -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh)
    val context = LocalContext.current

    // プレイスホルダー画像の生成
    val placeholder = ResourcesCompat.getDrawable(
        context.resources,
        R.drawable.avatar_placeholder,
        null,
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text(text = "プロフィール")
                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(onClick = onClickEdit ) {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = "プロフィールの編集",
                            modifier = Modifier.padding(end = 16.dp),
                        )
                        }
                    }

                }

            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick =  onClickHome ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "home"
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search"
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "notification"
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "email"
                        )
                    }
                    IconButton(onClick =  onClickProfile ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "accountcircle"
                        )
                    }
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                AsyncImage(
                    modifier = Modifier.size(120.dp),
                    // ImageRequestを作成して、画像取得できていない状態のプレイスホルダー設定
                    model = ImageRequest.Builder(context)
                        .data(statusBindingModel.avatar)
                        .placeholder(placeholder)
                        .error(placeholder)
                        .fallback(placeholder)
                        .setHeader("User-Agent", "Mozilla/5.0") // モックサーバーから画像取得する場合のみ追加
                        .build(),
                    contentDescription = stringResource(id = R.string.public_timeline_avatar_content_description),
                    contentScale = ContentScale.Crop,
                )

                Text(
                    text = buildAnnotatedString {
                        // appendで文字列セット
                        append(statusBindingModel.displayName)
                    },
                    maxLines = 1, // 文字列が複数行にならないように指定
                    overflow = TextOverflow.Ellipsis, // はみ出した分を「...」で表現
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6.copy(fontSize = 36.sp)// 文字を太字に
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                // 文字色を薄くするために、ContentAlpha.mediumを指定
                                color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
                            )
                        ) {
                            append(" @${statusBindingModel.username}")
                        }
                    },
                    maxLines = 1, // 文字列が複数行にならないように指定
                    overflow = TextOverflow.Ellipsis, // はみ出した分を「...」で表現
                    fontWeight = FontWeight.Bold, // 文字を太字に
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = buildAnnotatedString {
                        append(statusBindingModel.followingCount.toString())
                        append(" Following")
                    },
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6.copy(fontSize = 18.sp)// 文字を太字に
                )


                Text(
                    text = buildAnnotatedString {
                        append(statusBindingModel.followerCount.toString())
                        append(" Follower")
                    },
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6.copy(fontSize = 18.sp)// 文字を太字に
                )

                Spacer(modifier = Modifier.height(32.dp))

                //ページ下部に自分のタイムラインを表示
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                ) {
                    items(statusList) { item ->
                        MyStatusRow(statusBindingModel = item)
                    }
                }
                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    //modifier = Modifier.align(Alignment.TopCenter)
                )
                if (isLoading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProfileTemplatePreview() {
    Yatter2024Theme {
        Surface() {
            ProfileTemplate(
                statusBindingModel = StatusBindingModel(
                    id = "id",
                    displayName = "displayName",
                    username = "username",
                    avatar = null,
                    content = "preview content_default",
                    followingCount = 0,
                    followerCount = 0,
                    attachmentMediaList = listOf()
                ),
                statusList = listOf(
                    StatusBindingModel(
                        id = "id1",
                        displayName = "display name1",
                        username = "username1",
                        avatar = null,
                        content = "preview content1",
                        followingCount = 0,
                        followerCount = 0,
                        attachmentMediaList = listOf()
                    ),
                    StatusBindingModel(
                        id = "id2",
                        displayName = "display name2",
                        username = "username2",
                        avatar = null,
                        content = "preview content2",
                        followingCount = 0,
                        followerCount = 0,
                        attachmentMediaList = listOf()
                    ),
                ),
                isLoading = true,
                isRefreshing = false,
                onRefresh = {},
                onClickProfile = {},
                onClickHome = {},
                onClickEdit = {},
            )
        }
    }
}