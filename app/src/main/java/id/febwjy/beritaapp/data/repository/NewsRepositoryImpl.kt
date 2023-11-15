package id.febwjy.beritaapp.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.febwjy.beritaapp.data.model.NewsResponse
import id.febwjy.beritaapp.domain.repository.NewsRepository
import id.febwjy.beritaapp.network.NewsAPI
import id.febwjy.beritaapp.utils.Constant
import id.febwjy.beritaapp.utils.NetworkListResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Febby Wijaya on 14/11/2023.
 */
class NewsRepositoryImpl @Inject constructor(
    private val newsAPI: NewsAPI
) : NewsRepository {

    override suspend fun getNews(
        category: String,
        country: String
    ): Flow<NetworkListResult<List<NewsResponse.Articles>, NewsResponse>> {
        return flow {
            val response = newsAPI.getNews(
                api_key = Constant.API_KEY,
                category = category,
                country = country
            )
            if (response.isSuccessful) {
                val body = response.body()!!
                val news = mutableListOf<NewsResponse.Articles>()

                body.articles.forEach {
                    news.add(
                        NewsResponse.Articles(
                            author = it.author,
                            title = it.title,
                            description = it.description,
                            url = it.url,
                            urlToImage = it.urlToImage,
                            publishedAt = it.publishedAt,
                            content = it.content
                        )
                    )
                }
                emit(NetworkListResult.Success(news))
            } else {
                val type = object : TypeToken<NewsResponse>() {}.type
                val error = Gson().fromJson<NewsResponse>(
                    response.errorBody()!!.charStream(), type
                )!!
                error.message = response.message()
                emit(NetworkListResult.Error(error))
            }
        }
    }

}