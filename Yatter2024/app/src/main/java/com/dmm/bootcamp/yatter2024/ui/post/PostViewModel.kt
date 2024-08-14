package com.dmm.bootcamp.yatter2024.ui.post

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.common.navigation.PopBackDestination
import com.dmm.bootcamp.yatter2024.di.viewModelModule
import com.dmm.bootcamp.yatter2024.domain.service.GetMeService
import com.dmm.bootcamp.yatter2024.usecase.post.PostStatusUseCase
import com.dmm.bootcamp.yatter2024.usecase.post.PostStatusUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class PostViewModel(
    private val postStatusUseCase: PostStatusUseCase,
    private val getMeService: GetMeService,
) : ViewModel() {
    fun onCreate(){ //ユーザー情報取得
        viewModelScope.launch{
            _uiState.update{it.copy(isLoading = true)}

            val me = getMeService.execute()

            val snapshotBindingModel = uiState.value.bindingModel
            _uiState.update{it.copy(bindingModel = snapshotBindingModel.copy(avatarUrl = me?.avatar?.toString()),
                isLoading = false,
            )}

        }
    }

    fun onChangedStatusText(statusText: String){ //Statusを書き換えた時に呼び出される
        _uiState.update{it.copy(bindingModel = uiState.value.bindingModel.copy(statusText = statusText))}
    }

    fun onClickPost(){ //投稿ボタンを押す
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = postStatusUseCase.execute(
                content = uiState.value.bindingModel.statusText,
                attachmentList = listOf()
            )
            when(result){ //投稿完了したら画面を戻り、失敗した場合はそのまま
                PostStatusUseCaseResult.Success -> {
                    _destination.value = PopBackDestination
                }
                is PostStatusUseCaseResult.Failure -> {
                    //エラー表示
                    Log.ERROR
                }
            }
            _uiState.update{it.copy(isLoading = false)}
        }
    }

    fun onClickNavIcon(){ //タイムライン画面に戻るためのボタンを押す
        _destination.value = PopBackDestination
    }

    fun onCompleteNavigation(){ //Destinationを初期化
        _destination.value = null
    }

    private val _uiState: MutableStateFlow<PostUiState> = MutableStateFlow(PostUiState.empty())
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()
}