package com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.converter

import com.dmm.bootcamp.yatter2024.domain.model.Account
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.ProfileBindingModel

object ProfileConverter {
    fun convertToBindingModel(account: Account): ProfileBindingModel =
        ProfileBindingModel(
            id = account.id.value,
            username = account.username.value,
            displayName = account.displayName,
            note = account.note,
            avatar = account.avatar.toString(),
            header = account.header.toString(),
            followingCount = account.followingCount,
            followerCount = account.followerCount,
        )
}