package com.dmm.bootcamp.yatter2024.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.common.navigation.PopBackDestination
import com.dmm.bootcamp.yatter2024.domain.service.GetMeService
import com.dmm.bootcamp.yatter2024.usecase.image.ImageStatusUseCase
import com.dmm.bootcamp.yatter2024.usecase.image.ImageStatusUseCaseResult
import com.dmm.bootcamp.yatter2024.usecase.post.PostStatusUseCase
import com.dmm.bootcamp.yatter2024.usecase.post.PostStatusUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class PostViewModel(
    private val postStatusUseCase: PostStatusUseCase,
    private val imageStatusUseCase: ImageStatusUseCase,
    private val getMeService: GetMeService,
): ViewModel() {
    private val _uiState: MutableStateFlow<PostUiState> = MutableStateFlow(PostUiState.empty())
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    private val _callGetImage = MutableStateFlow<Unit?>(null)
    val callGetImage : StateFlow<Unit?> = _callGetImage.asStateFlow()
    fun onCreate() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val me = getMeService.execute()

            val snapshotBindingModel = uiState.value.bindingModel
            _uiState.update {
                it.copy(
                    bindingModel = snapshotBindingModel.copy(avatarUrl = me?.avatar?.toString()),
                    isLoading = false,
                )
            }
        }
    }
    fun getImage(){
        _callGetImage.value = Unit
    }
    fun onAttachedImage(file: File){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val snapshotBindingModel = uiState.value.bindingModel
            val result = imageStatusUseCase.execute(
                image = file,
            )
            when(result){
                is ImageStatusUseCaseResult.Success -> {
                    _uiState.update{
                        it.copy(bindingModel = snapshotBindingModel.copy(
                            attachmentList = snapshotBindingModel.attachmentList + listOf(result.url),
                            mediaIdList = snapshotBindingModel.mediaIdList + listOf(result.id)
                        ))
                    }
                }
                is ImageStatusUseCaseResult.Failure -> {

                }
            }
            _uiState.update {it.copy(isLoading = false)}
        }
    }
    fun onChangedStatusText(statusText: String){
        _uiState.update { it.copy(bindingModel = uiState.value.bindingModel.copy(statusText = statusText))}
    }

    fun onClickPost(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true)}
            val result = postStatusUseCase.execute(
                content = uiState.value.bindingModel.statusText,
                attachmentList = uiState.value.bindingModel.mediaIdList,
            )
            when (result) {
                PostStatusUseCaseResult.Success -> {
                    _destination.value = PopBackDestination
                }
                is PostStatusUseCaseResult.Failure -> {

                }
            }
            _uiState.update{ it.copy(isLoading = false)}
        }
    }

    fun onClickNavIcon() {
        _destination.value = PopBackDestination
    }

    fun onCompleteNavigation() {
        _destination.value = null
    }
}