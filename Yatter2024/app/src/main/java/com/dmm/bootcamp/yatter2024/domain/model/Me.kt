package com.dmm.bootcamp.yatter2024.domain.model

import java.net.URL

abstract class Me(
  id: AccountId,
  username: Username,
  displayName: String?,
  note: String?,
  avatar: URL?,
  header: URL?,
  followingCount: Int,
  followerCount: Int,
  createdAt: String,
) : Account(
  id,
  username,
  displayName,
  note,
  avatar,
  header,
  followingCount,
  followerCount,
  createdAt,
) {

  abstract suspend fun follow(username: Username)

  abstract suspend fun unfollow(username: Username)
}
