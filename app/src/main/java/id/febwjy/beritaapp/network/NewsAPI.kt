package id.febwjy.beritaapp.network

import id.febwjy.beritaapp.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Febby Wijaya on 14/11/2023.
 */
interface NewsAPI {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") api_key: String
    ): Response<NewsResponse>

}