package com.dmm.bootcamp.yatter2024.di

import com.dmm.bootcamp.yatter2024.usecase.impl.login.LoginUseCaseImpl
import com.dmm.bootcamp.yatter2024.usecase.impl.post.PostStatusUseCaseImpl
import com.dmm.bootcamp.yatter2024.usecase.impl.register.RegisterAccountUseCaseImpl
import com.dmm.bootcamp.yatter2024.usecase.impl.update.UpdateAccountUseCaseImpl
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter2024.usecase.post.PostStatusUseCase
import com.dmm.bootcamp.yatter2024.usecase.register.RegisterAccountUseCase
import com.dmm.bootcamp.yatter2024.usecase.update.UpdateAccountUseCase
import org.koin.dsl.module

internal val useCaseModule = module {
  factory<PostStatusUseCase> { PostStatusUseCaseImpl(get()) }
  factory<RegisterAccountUseCase> { RegisterAccountUseCaseImpl(get(), get(), get()) }
  factory<LoginUseCase> { LoginUseCaseImpl(get(), get()) }
  factory<RegisterAccountUseCase> { RegisterAccountUseCaseImpl(get(), get(), get()) }
  factory<UpdateAccountUseCase> { UpdateAccountUseCaseImpl(get(), get()) }
}

