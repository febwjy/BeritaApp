package id.febwjy.beritaapp.domain.repository

import id.febwjy.beritaapp.data.model.NewsResponse
import id.febwjy.beritaapp.utils.NetworkListResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by Febby Wijaya on 14/11/2023.
 */
interface NewsRepository {

    suspend fun getNews(category: String, country: String):
            Flow<NetworkListResult<List<NewsResponse.Articles>, NewsResponse>>

}