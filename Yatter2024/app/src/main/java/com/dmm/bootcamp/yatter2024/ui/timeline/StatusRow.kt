package com.dmm.bootcamp.yatter2024.ui.timeline

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Colors
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import androidx.core.content.res.ResourcesCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dmm.bootcamp.yatter2024.R
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.MediaBindingModel
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.StatusBindingModel

@Composable
fun StatusRow(
    statusBindingModel: StatusBindingModel,
    modifier: Modifier = Modifier,
    onClickAvatar: (String) -> Unit,
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ){
        val context = LocalContext.current
        val placeholder = ResourcesCompat.getDrawable(
            context.resources,
            R.drawable.avatar_placeholder,
            null,
        )
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clickable { onClickAvatar(statusBindingModel.username) },
            model = ImageRequest.Builder(context)
                .data(statusBindingModel.avatar)
                .placeholder(placeholder)
                .error(placeholder)
                .fallback(placeholder)
                .setHeader("User-Agent","Mozilla/5.0")
                .build(),
            contentDescription = stringResource(id = R.string.public_timeline_avatar_content_description),
            contentScale = ContentScale.Crop,
        )

        Column (verticalArrangement = Arrangement.spacedBy(4.dp)){
            Text(
                text = buildAnnotatedString {
                    append(statusBindingModel.displayName)
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
                        )
                    ){
                        append(" @${statusBindingModel.username}")
                    }
                },
                maxLines = 1, // Each Column one line
                overflow = TextOverflow.Ellipsis, // Over screen express [...]
                fontWeight = FontWeight.Bold,
            )
            Text(text = statusBindingModel.content)
            LazyRow{
                items(statusBindingModel.attachmentMediaList) {attachmentMedia ->
                    AsyncImage(
                        model = attachmentMedia.url,
                        contentDescription = attachmentMedia.description
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
            IconButton(
                modifier = Modifier.size(40.dp),
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    tint = Color.Cyan,
                    contentDescription = "Favorite",
                )
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
                    displayName = "mito",
                    username = "mitohato14",
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
                ),
                onClickAvatar = {},
            )
        }
    }
}