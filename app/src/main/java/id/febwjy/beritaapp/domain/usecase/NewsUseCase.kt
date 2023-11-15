package id.febwjy.beritaapp.domain.usecase

import id.febwjy.beritaapp.data.model.NewsResponse
import id.febwjy.beritaapp.domain.repository.NewsRepository
import id.febwjy.beritaapp.utils.NetworkListResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Febby Wijaya on 14/11/2023.
 */
class NewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
){

    suspend fun invoke(category: String, country: String) :
            Flow<NetworkListResult<List<NewsResponse.Articles>, NewsResponse>> {
        return newsRepository.getNews(category, country)
    }

}