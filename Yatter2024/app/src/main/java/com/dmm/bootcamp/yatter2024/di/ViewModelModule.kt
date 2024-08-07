package com.dmm.bootcamp.yatter2024.di


import com.dmm.bootcamp.yatter2024.ui.MainViewModel
import com.dmm.bootcamp.yatter2024.ui.login.LoginViewModel
import com.dmm.bootcamp.yatter2024.ui.post.PostViewModel
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileViewModel
import com.dmm.bootcamp.yatter2024.ui.register.RegisterViewModel
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineViewModel
import com.dmm.bootcamp.yatter2024.ui.timelinedetail.TimelineDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
  viewModel { MainViewModel(get()) }
  viewModel { PublicTimelineViewModel(get()) }
  viewModel { PostViewModel(get(), get()) }
  viewModel { RegisterViewModel(get()) }
  viewModel { LoginViewModel(get()) }
  viewModel { parameters -> ProfileViewModel(username = parameters.get(),get(),get()) }
  viewModel { parameters -> TimelineDetailViewModel(id = parameters.get(),get())}
}
