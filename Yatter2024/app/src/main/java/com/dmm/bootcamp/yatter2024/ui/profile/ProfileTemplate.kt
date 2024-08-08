package com.dmm.bootcamp.yatter2024.ui.profile

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.F
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.dmm.bootcamp.yatter2024.R
import com.dmm.bootcamp.yatter2024.domain.model.Status
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.StatusBindingModel
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme
import java.net.URL

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileTemplate(
    profileBindingModel: ProfileBindingModel,
    statusList: List<StatusBindingModel>,
    isLoading: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onClickPost: () -> Unit,
    onClickLogout: () -> Unit,
    onClickFollow: () -> Unit,
    onClickFollower: () -> Unit,
    onCliCkSetting: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = profileBindingModel.username)
                },
                navigationIcon = {
                    IconButton(onClick = onClickLogout){
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ExitToApp,
                            contentDescription = "logout"
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
        }
    ) {paddingValues ->
        val pullRefershState = rememberPullRefreshState(isRefreshing, onRefresh)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pullRefresh(pullRefershState),
            contentAlignment = Alignment.Center,
        ){
            Column(
                modifier = Modifier.fillMaxWidth(),
            ){
                val context = LocalContext.current
                AsyncImage(
                    modifier =Modifier.fillMaxWidth().height(80.dp),
                    model = ImageRequest.Builder(context)
                        .data(profileBindingModel.header)
                        .setHeader("User-Agent","Mozilla/5.0")
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                Row{
                    AsyncImage(
                        modifier =Modifier.size(48.dp),
                        model = ImageRequest.Builder(context)
                            .data(profileBindingModel.avatar)
                            .setHeader("User-Agent","Mozilla/5.0")
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                    Column{
                        Text(profileBindingModel.username)
                        Text(profileBindingModel.id ?: "none_id") // idを入れたい．Identifier→String
                    }
                    if(profileBindingModel.username == profileBindingModel.myname){
                        Button(onClick = onCliCkSetting){
                            Text("Edit")
                        }
                    }
                }
                Text(profileBindingModel.note ?: "ノートはありません")
                Row{
                    TextButton(onClick = onClickFollow) {
                        Text("${profileBindingModel.numFollow} Follow")
                    }
                    TextButton(onClick = onClickFollower) {
                        Text("${profileBindingModel.numFollower} Follower")
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                ) {
                    items(statusList){item ->
                        StatusRow(item)
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefershState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
            if(isLoading) {
                CircularProgressIndicator()
            }
        }

    }
}

