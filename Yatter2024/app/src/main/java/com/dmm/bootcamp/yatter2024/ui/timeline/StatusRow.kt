package com.dmm.bootcamp.yatter2024.ui.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import androidx.core.content.res.ResourcesCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dmm.bootcamp.yatter2024.R
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.MediaBindingModel
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.StatusBindingModel

//class StatusRow {
//}

@Composable
fun StatusRow(statusBindingModel: StatusBindingModel, modifier: Modifier = Modifier ){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), //Rowは横幅を取れるだけとり、縦方向(vertical)に4dpのpaddingを当てる
        horizontalArrangement = Arrangement.spacedBy(8.dp), //横一列に並べるコンポーザブル同士の隙間を8dp空ける
    ){
        val context = LocalContext.current

        val placeholder = ResourcesCompat.getDrawable(
            context.resources,
            R.drawable.avatar_placeholder,
            null,
        )
        //アバター画像の設置
        AsyncImage(
            modifier = Modifier.size(48.dp),
            // ImageRequestを作成して、画像取得できていない状態のプレイスホルダー設定
            model = ImageRequest.Builder(context)
                .data(statusBindingModel.avatar)
                .placeholder(placeholder)
                .error(placeholder)
                .fallback(placeholder)
                .setHeader("User-Agent", "Mozilla/5.0") // モックサーバーから画像取得する場合のみ追加
                .build(),
            contentDescription = stringResource(id = R.string.public_timeline_avatar_content_description),
            contentScale = ContentScale.Crop
        )
        //表示名とユーザー名、statusの内容を縦方向に並べる
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)){
            //表示名とユーザー名、statusの内容を横方向に並べる
            Text(
                text = buildAnnotatedString {
                    append(statusBindingModel.displayName)
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
                        )
                    ){
                        append("@${statusBindingModel.username}")
                    }
                },
                maxLines = 1, //複数行にならないように
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold, //太字に
            )
            Text(text = statusBindingModel.content)
            LazyRow {
                // itemsの第一引数に並べたいデータセットを渡す
                items(statusBindingModel.attachmentMediaList) { attachmentMedia ->
                    // データ1件あたりに表示したいコンポーザブルを呼び出す
                    AsyncImage(
                        model = attachmentMedia.url,
                        contentDescription = attachmentMedia.description
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun StatusRowPreview() {
    Yatter2024Theme {
        Surface {
            StatusRow(
                statusBindingModel = StatusBindingModel(
                    id = "id",
                    displayName = "hato",
                    username = "hato72",
                    avatar = "https://avatars.githubusercontent.com/u/19385268?v=4",
                    content = "preview content",
                    attachmentMediaList = listOf(
                        MediaBindingModel(
                            id = "id",
                            type = "image",
                            url = "https://avatars.githubusercontent.com/u/39693306?v=4",
                            description = "icon"
                        )
                    )
                )
            )
        }
    }
}