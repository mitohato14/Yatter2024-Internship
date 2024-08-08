package com.dmm.bootcamp.yatter2024.domain.repository

import com.dmm.bootcamp.yatter2024.domain.model.AccountId
import com.dmm.bootcamp.yatter2024.domain.model.Media
import com.dmm.bootcamp.yatter2024.domain.model.MediaId
import com.dmm.bootcamp.yatter2024.domain.model.Status
import com.dmm.bootcamp.yatter2024.domain.model.StatusId
import java.io.File

interface StatusRepository {
  suspend fun findById(id: StatusId): Status?

  suspend fun findAllPublic(): List<Status>

  suspend fun findAllHome(): List<Status>
  suspend fun findStatusByUsername(username:String): List<Status>

  suspend fun registerImage(image:File): Media

  suspend fun create(
    content: String,
    attachmentList: List<MediaId>
  ): Status

  suspend fun delete(
    status: Status
  )
}
