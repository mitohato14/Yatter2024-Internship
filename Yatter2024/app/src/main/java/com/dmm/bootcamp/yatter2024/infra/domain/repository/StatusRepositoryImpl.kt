package com.dmm.bootcamp.yatter2024.infra.domain.repository

import com.dmm.bootcamp.yatter2024.domain.model.Status
import com.dmm.bootcamp.yatter2024.domain.model.StatusId
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.auth.TokenProvider
import com.dmm.bootcamp.yatter2024.common.navigation.PopBackDestination.builder
import com.dmm.bootcamp.yatter2024.domain.model.AccountId
import com.dmm.bootcamp.yatter2024.domain.model.Media
import com.dmm.bootcamp.yatter2024.domain.model.MediaId
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.infra.api.YatterApi
import com.dmm.bootcamp.yatter2024.infra.api.json.PostStatusJson
import com.dmm.bootcamp.yatter2024.infra.api.json.PostStatusMediaJson
import com.dmm.bootcamp.yatter2024.infra.domain.converter.MediaConverter
import com.dmm.bootcamp.yatter2024.infra.domain.converter.MediaResponseConverter
import com.dmm.bootcamp.yatter2024.infra.domain.converter.StatusConverter
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.Dictionary

class StatusRepositoryImpl(
  private val yatterApi: YatterApi,
  private val tokenProvider: TokenProvider,
  private val accountRepository: AccountRepository,
) : StatusRepository {
  override suspend fun findById(id: StatusId): Status? {
    TODO("Not yet implemented")
  }

  override suspend fun findAllPublic(): List<Status> = withContext(IO) {
    val statusList = yatterApi.getPublicTimeline()
    StatusConverter.convertToDomainModel(statusList)
  }

  override suspend fun findAllHome(): List<Status> = withContext(IO) {
    val statusList = yatterApi.getHomeTimeline(tokenProvider.provide())
    StatusConverter.convertToDomainModel(statusList)
  }

  override suspend fun findStatusByUsername(username: String): List<Status> = withContext(IO){
    val allStatusList = findAllPublic()
    val statusList = allStatusList.filter{
      it.account.username == Username(username)
    }
    statusList
  }
  override suspend fun registerImage(
    image: File,
  ): Media {

    val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
    builder.addFormDataPart(
      "file", image.name, image.asRequestBody("image/jpg".toMediaTypeOrNull())
    )
    val body = builder.build()
    val imageJson = yatterApi.registerMedia(
      body
    )
    val resImage = MediaResponseConverter.convertToDomainModel(listOf(imageJson))
    return resImage.first()

  }
  override suspend fun create(
    content: String,
    attachmentList: List<MediaId>
  ): Status = withContext(IO) {
    val postMediaJson: MutableList<PostStatusMediaJson> = mutableListOf()
    for (attachment in attachmentList){
      postMediaJson.add(PostStatusMediaJson( mediaId = attachment.value.toInt(),description = ""))
    }
    val statusJson = yatterApi.postStatus(
      tokenProvider.provide(),
      PostStatusJson(
        content,
        postMediaJson
      )
    )
    StatusConverter.convertToDomainModel(statusJson)
  }
  override suspend fun delete(status: Status) {
    TODO("Not yet implemented")
  }
}
