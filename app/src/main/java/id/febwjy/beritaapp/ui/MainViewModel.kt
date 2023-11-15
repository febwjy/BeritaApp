package id.febwjy.beritaapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Created by Febby Wijaya on 15/11/2023.
 */

@HiltViewModel
open class MainViewModel @Inject constructor(application: Application): AndroidViewModel(application){

    val _state = MutableStateFlow<BaseState>(BaseState.Init)
    val mState: StateFlow<BaseState> get() = _state

    val _loadMore = MutableStateFlow(true)
    val loadMore: StateFlow<Boolean> get() = _loadMore

    fun showToast(message: String) {
        _state.value = BaseState.ShowToast(message)
    }

    fun showLoading() {
        _state.value = BaseState.IsLoading(true)
    }

    fun dismissLoading() {
        _state.value = BaseState.IsLoading(false)
    }

}

sealed class BaseState {
    object Init : BaseState()
    data class IsLoading(val isLoading: Boolean) : BaseState()
    data class ShowToast(val message: String) : BaseState()
}