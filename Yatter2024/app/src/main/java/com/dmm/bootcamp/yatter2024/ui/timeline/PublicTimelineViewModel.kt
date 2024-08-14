package com.dmm.bootcamp.yatter2024.ui.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.common.navigation.PopBackDestination
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.ui.login.LoginDestination
import com.dmm.bootcamp.yatter2024.ui.post.PostDestination
import com.dmm.bootcamp.yatter2024.ui.post.PostPage
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.converter.StatusConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PublicTimelineViewModel(
    private val statusRepository: StatusRepository,
) : ViewModel() {
    //外部に公開しないが変更できる値
    private val _uiState: MutableStateFlow<PublicTimelineUiState> =
        MutableStateFlow(PublicTimelineUiState.empty())
    //外部に公開するが変更できない値
    val uiState: StateFlow<PublicTimelineUiState> = _uiState.asStateFlow()

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()


    private suspend fun fetchPublicTimeline(){
        val statusList = statusRepository.findAllPublic() //status一覧を取得
        _uiState.update{
            it.copy(
                statusList = StatusConverter.convertToBindingModel(statusList), //statusListの更新
            )
        }
    }

    fun onResume(){
        viewModelScope.launch { //コルーチン起動
            _uiState.update{ it.copy(isLoading = true)}
            fetchPublicTimeline() //更新
            _uiState.update {it.copy(isLoading = false)}
        }
    }

    fun onRefresh(){ //画面のリフレッシュ
        viewModelScope.launch{
            _uiState.update { it.copy(isRefreshing = true) }
            fetchPublicTimeline()
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }

    fun onClickPost(){ //ツイート画面に遷移するボタンが押された時の処理
        _destination.value = PostDestination()
    }

    fun onClickNavIcon(){ //ログイン画面に戻る処理
        _destination.value = LogoutDestination()
    }

    fun onCompleteNavigation(){
        _destination.value = null
    }
}