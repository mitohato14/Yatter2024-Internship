package com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.converter

import com.dmm.bootcamp.yatter2024.domain.model.Account
import com.dmm.bootcamp.yatter2024.domain.model.Me
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.ProfileBindingModel

object ProfileConverter {
    fun convertToBindingModel(profileList: List<Me>): List<ProfileBindingModel> =
        profileList.map { convertToBindingModel(it) }

    fun convertToBindingModel(account: Account): ProfileBindingModel =
        ProfileBindingModel(
            id = account.id.value,
            displayName = account.displayName ?: "",
            username = account.username.value,
            note = account.note ?: "",
            avatar = account.avatar?.toString(),
            header = account.header?.toString(),
            followingCount = account.followingCount,
            followerCount = account.followerCount,
        )
}