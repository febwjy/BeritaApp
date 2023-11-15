package id.febwjy.beritaapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Febby Wijaya on 14/11/2023.
 */
class NewsResponse {

    @SerializedName("status")
    var status: String? = null

    @SerializedName("totalResult")
    var totalResult: Int? = null

    @SerializedName("code")
    var code: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("articles")
    var articles: List<Articles> = emptyList()

    data class Articles(
        val author: String? = null,
        val title: String? = null,
        val description: String? = null,
        val url: String? = null,
        val urlToImage: String? = null,
        val publishedAt: String? = null,
        val content: String? = null,

    )
}