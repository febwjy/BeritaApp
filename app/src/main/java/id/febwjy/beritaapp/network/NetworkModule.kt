package id.febwjy.beritaapp.network

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.febwjy.beritaapp.data.repository.NewsRepositoryImpl
import id.febwjy.beritaapp.domain.repository.NewsRepository
import id.febwjy.beritaapp.utils.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Febby Wijaya on 14/11/2023.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideNewsAPI() : NewsAPI {
        val httpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(logging)

        return Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(NewsAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: NewsAPI) : NewsRepository {
        return NewsRepositoryImpl(api)
    }

}