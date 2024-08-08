package com.dmm.bootcamp.yatter2024.usecase.impl.post

import android.accounts.AuthenticatorException
import com.dmm.bootcamp.yatter2024.domain.model.MediaId
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.usecase.post.PostStatusUseCase
import com.dmm.bootcamp.yatter2024.usecase.post.PostStatusUseCaseResult
import java.io.File

class PostStatusUseCaseImpl(
  private val statusRepository: StatusRepository
) : PostStatusUseCase {
  override suspend fun execute(
    content: String,
    attachmentList: List<MediaId>
  ): PostStatusUseCaseResult {
    if (content == "" && attachmentList.isEmpty()) {
      return PostStatusUseCaseResult.Failure.EmptyContent
    }

    return try {
      statusRepository.create(
        content = content,
        attachmentList = attachmentList
      )

      PostStatusUseCaseResult.Success
    } catch (e: AuthenticatorException) {
      PostStatusUseCaseResult.Failure.NotLoggedIn
    } catch (e: Exception) {
      PostStatusUseCaseResult.Failure.OtherError(e)
    }
  }
}
