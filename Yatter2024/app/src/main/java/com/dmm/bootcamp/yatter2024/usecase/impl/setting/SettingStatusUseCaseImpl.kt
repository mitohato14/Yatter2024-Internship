package com.dmm.bootcamp.yatter2024.usecase.impl.setting

import android.accounts.AuthenticatorException
import com.dmm.bootcamp.yatter2024.domain.model.Me
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.usecase.setting.SettingStatusUseCase
import com.dmm.bootcamp.yatter2024.usecase.setting.SettingStatusUseCaseResult
import java.io.File
import java.net.URL

class SettingStatusUseCaseImpl(
    private val accountRepository: AccountRepository
) : SettingStatusUseCase {
    override suspend fun execute(
        me: Me?,
        newDisplayName: String?,
        newNote: String?,
        newAvatar: File?,
        newHeader: File?,
    ): SettingStatusUseCaseResult {
        return try {
            accountRepository.update(
                me = me,
                newDisplayName = newDisplayName,
                newNote = newNote,
                newAvatar = newAvatar,
                newHeader = newHeader,
            )

            SettingStatusUseCaseResult.Success
        }catch (e: AuthenticatorException){
            SettingStatusUseCaseResult.Failure.NotLoggedIn
        } catch (e: Exception) {
            SettingStatusUseCaseResult.Failure.OtherError(e)
        }
    }
}