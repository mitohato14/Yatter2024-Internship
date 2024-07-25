package com.dmm.bootcamp.yatter2024.infra.domain.repository

import com.dmm.bootcamp.yatter2024.auth.TokenProvider
import com.dmm.bootcamp.yatter2024.domain.model.Account
import com.dmm.bootcamp.yatter2024.domain.model.Me
import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.infra.api.YatterApi
import com.dmm.bootcamp.yatter2024.infra.api.json.CreateAccountJson
import com.dmm.bootcamp.yatter2024.infra.api.json.UpdateAccountJson
import com.dmm.bootcamp.yatter2024.infra.domain.converter.AccountConverter
import com.dmm.bootcamp.yatter2024.infra.domain.converter.MeConverter
import com.dmm.bootcamp.yatter2024.infra.pref.MePreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.net.URL

class AccountRepositoryImpl(
  private val yatterApi: YatterApi,
  private val mePreferences: MePreferences,
  private val tokenProvider: TokenProvider,
) : AccountRepository {
  override suspend fun create(
    username: Username,
    password: Password
  ): Me = withContext(Dispatchers.IO) {
    val accountJson = yatterApi.createNewAccount(
      CreateAccountJson(
        username = username.value,
        password = password.value
      )
    )

    MeConverter.convertToMe(AccountConverter.convertToDomainModel(accountJson))
  }

  override suspend fun findMe(): Me? = withContext(Dispatchers.IO) {
    val username = mePreferences.getUsername() ?: return@withContext null
    if (username.isEmpty()) return@withContext null
    val account = findByUsername(username = Username(username)) ?: return@withContext null
    MeConverter.convertToMe(account)
  }

  override suspend fun findByUsername(username: Username): Account? = withContext(Dispatchers.IO) {
    val accountJson = yatterApi.getAccountByUsername(username = username.value)
    AccountConverter.convertToDomainModel(accountJson)
  }

  override suspend fun update(
    me: Me?,
    newDisplayName: String?,
    newNote: String?,
    newAvatar: File?,
    newHeader: File?
  ): Me = withContext(Dispatchers.IO) {
    val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
    if(newAvatar != null){
      builder.addFormDataPart(
        "avatar",
        newAvatar.name,
        newAvatar.asRequestBody("image/jpg".toMediaTypeOrNull())
      )
    }
    if(newHeader != null){
      builder.addFormDataPart(
        "header",
        newHeader.name,
        newHeader.asRequestBody("image/jpg".toMediaTypeOrNull())
      )
    }
    if(newDisplayName != null) {
      builder.addFormDataPart("display_name", newDisplayName)
    }
    if(newNote != null){
      builder.addFormDataPart("note",newNote)
    }
    val body = builder.build()
    val accountJson = yatterApi.updateAccount(tokenProvider.provide(),body)
    val account = AccountConverter.convertToDomainModel(accountJson)
    MeConverter.convertToMe(account)
  }
}
