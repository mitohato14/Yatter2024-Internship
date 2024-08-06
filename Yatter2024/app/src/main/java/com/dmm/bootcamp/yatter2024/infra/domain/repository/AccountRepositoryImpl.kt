package com.dmm.bootcamp.yatter2024.infra.domain.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.net.URL
import java.util.UUID

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

  @RequiresApi(Build.VERSION_CODES.O)
  override suspend fun update(
    me: Me,
    newDisplayName: String?,
    newNote: String?,
    newAvatar: URL?,
    newHeader: URL?
  ): Me = withContext(Dispatchers.IO) {
    Log.d("AccountRepositoryImpl", "update")
    Log.d("AccountRepositoryImpl", "newDisplayName: $newDisplayName")
    Log.d("AccountRepositoryImpl", "newNote: $newNote")

    var id = UUID.randomUUID().toString()
    var avatarFile = newAvatar?.let { createMultipartBodyPartFromUrl(
      "avatar",
      it.toString(),
      id + "avatar.jpg",
      "image/jpeg")
    }
    var headerFile = newHeader?.let { createMultipartBodyPartFromUrl(
      "header", it.toString(),
      id + "header.jpg",
      "image/jpeg")
    }
    val updateAccountJson = UpdateAccountJson(
      displayName = newDisplayName,
      note = newNote,
      avatar = avatarFile,
      header = headerFile,
    )

    val accountJson = yatterApi.updateAccount(
      token = tokenProvider.provide(),
      displayName = createRequestBody(updateAccountJson.displayName),
      note = createRequestBody(updateAccountJson.note),
      avatar = updateAccountJson.avatar,
      header = updateAccountJson.header
    )
    MeConverter.convertToMe(AccountConverter.convertToDomainModel(accountJson))
  }

  private suspend fun createMultipartBodyPartFromUrl(
    fieldName: String,
    url: String,
    fileName: String,
    mimeType: String
  ): MultipartBody.Part? {
    val byteArray = createByteArrayFromUrl(url)
    return byteArray?.let { createMultipartBodyPartFromByteArray(fieldName, it, fileName, mimeType) }
  }

  private suspend fun createByteArrayFromUrl(url: String): ByteArray? {
    return withContext(Dispatchers.IO) {
      val client = OkHttpClient()
      val request = Request.Builder().url(url).build()
      client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
          response.body?.bytes()
        } else {
          null
        }
      }
    }
  }

  private fun createMultipartBodyPartFromByteArray(
    fieldName: String,
    byteArray: ByteArray,
    fileName: String,
    mimeType: String
  ): MultipartBody.Part {
    val requestBody = RequestBody.create(mimeType.toMediaTypeOrNull(), byteArray)
    return MultipartBody.Part.createFormData(fieldName, fileName, requestBody)
  }

//  private suspend fun createMultipartBodyPartFromUrl(
//    fieldName: String,
//    url: String,
//    fileName: String,
//    mimeType: String
//  ): MultipartBody.Part? {
//    val client = OkHttpClient()
//    val request = Request.Builder().url(url).build()
//
//    return try {
//      client.newCall(request).execute().use { response ->
//        if (response.isSuccessful) {
//          val byteArray = response.body?.bytes()
//          byteArray?.let {
//            val requestBody = RequestBody.create(mimeType.toMediaTypeOrNull(), it)
//            MultipartBody.Part.createFormData(fieldName, fileName, requestBody)
//          }
//        } else {
//          null
//        }
//      }
//    } catch (e: Exception) {
//      e.printStackTrace()
//      null
//    }
//  }

  private fun createRequestBody(value: String?): RequestBody? {
    return value?.let {
      RequestBody.create("text/plain; charset=utf-8".toMediaTypeOrNull(), it)
    }
  }
}
