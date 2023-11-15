package id.febwjy.beritaapp.ui.topheadlines

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.febwjy.beritaapp.data.model.NewsResponse
import id.febwjy.beritaapp.domain.usecase.NewsUseCase
import id.febwjy.beritaapp.ui.MainViewModel
import id.febwjy.beritaapp.utils.NetworkListResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Febby Wijaya on 15/11/2023.
 */

@HiltViewModel
class TopHeadlinesViewModel @Inject constructor(
    private val getNewsUseCase: NewsUseCase, application: Application
) :  MainViewModel(application) {

    private val newsList = MutableStateFlow<List<NewsResponse.Articles>>(mutableListOf())
    val mNewsList: StateFlow<List<NewsResponse.Articles>> get() = newsList

    fun getTopHeadlines(category: String, country: String) {
        viewModelScope.launch {
            getNewsUseCase.invoke(category, country).onStart {
                showLoading()
            }.catch {exception ->
                dismissLoading()
                Log.e("Error", exception.message.toString())
            }.collect { result ->
                when (result) {
                    is NetworkListResult.Success -> {
                        dismissLoading()
                        newsList.value = result.data
                        _loadMore.value = result.data.size > 10
                    }
                    is NetworkListResult.Error -> {
                        dismissLoading()
                        Log.e("result", result.rawResponse.message!!)
                    }
                }
            }
        }
    }

}