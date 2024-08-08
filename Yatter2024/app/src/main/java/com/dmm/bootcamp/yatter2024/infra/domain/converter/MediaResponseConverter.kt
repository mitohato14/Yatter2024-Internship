package com.dmm.bootcamp.yatter2024.infra.domain.converter

import com.dmm.bootcamp.yatter2024.domain.model.Media
import com.dmm.bootcamp.yatter2024.domain.model.MediaId
import com.dmm.bootcamp.yatter2024.infra.api.json.MediaJson
import com.dmm.bootcamp.yatter2024.infra.api.json.MediaResponseJson

object MediaResponseConverter {
    fun convertToDomainModel(jsonList: List<MediaResponseJson>): List<Media> =
        jsonList.map { convertToDomainModel(it) }

    private fun convertToDomainModel(json: MediaResponseJson): Media = Media(
        id = MediaId(value = json.id.toString()),
        type = json.type ?: "",
        url = json.url,
        description = json.description ?: "",
    )
}