package com.dmm.bootcamp.yatter2024.usecase.impl.update

import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.infra.pref.MePreferences
import com.dmm.bootcamp.yatter2024.usecase.update.UpdateAccountUseCase
import com.dmm.bootcamp.yatter2024.usecase.update.UpdateAccountUseCaseResult
import java.net.URL

class UpdateAccountUseCaseImpl(
    private val accountRepository: AccountRepository,
    private val mePreferences: MePreferences,
) : UpdateAccountUseCase {
    override suspend fun execute(
        displayName: String?,
        note: String?,
        avatar: URL?,
        header: URL?,
    ): UpdateAccountUseCaseResult {
        runCatching {
            val me = accountRepository.findMe()
            val newMe =  accountRepository.update(
                me?: return UpdateAccountUseCaseResult.Failure.NotFoundAccount,
                displayName,
                note,
                avatar,
                header,
            )
            mePreferences.putUserName(newMe.username.value)
        }.onFailure {
            return UpdateAccountUseCaseResult.Failure.UpdateAccountError(it)
        }
        return UpdateAccountUseCaseResult.Success
    }
}