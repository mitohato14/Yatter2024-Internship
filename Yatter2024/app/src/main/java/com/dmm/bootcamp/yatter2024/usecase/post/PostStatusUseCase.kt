package com.dmm.bootcamp.yatter2024.usecase.post

import com.dmm.bootcamp.yatter2024.domain.model.MediaId
import java.io.File

interface PostStatusUseCase {
  suspend fun execute(
    content: String,
    attachmentList: List<MediaId>
  ): PostStatusUseCaseResult
}
