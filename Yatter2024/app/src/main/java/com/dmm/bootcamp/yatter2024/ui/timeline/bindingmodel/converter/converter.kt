package com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.converter

import com.dmm.bootcamp.yatter2024.domain.model.Media
import com.dmm.bootcamp.yatter2024.domain.model.Status
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.MediaBindingModel
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.StatusBindingModel

object MediaConverter {
    fun convertToBindingModel(mediaList: List<Media>): List<MediaBindingModel> =
        mediaList.map { convertToBindingModel(it) }

    private fun convertToBindingModel(media: Media): MediaBindingModel = MediaBindingModel(
        id = media.id.value,
        type = media.type,
        url = media.url,
        description = media.description,
    )
}

object StatusConverter {
    fun convertToBindingModel(statusList: List<Status>): List<StatusBindingModel> =
        statusList.map { convertToBindingModel(it) }

    private fun convertToBindingModel(status: Status): StatusBindingModel =
        StatusBindingModel(
            id = status.id.value,
            displayName = status.account.displayName ?: "",
            username = status.account.username.value,
            avatar = status.account.avatar?.toString(),
            content = status.content,
            attachmentMediaList = MediaConverter.convertToBindingModel(status.attachmentMediaList)
        )
}