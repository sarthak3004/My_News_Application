package com.sarthak.mynewsapplication.data.repository

import android.util.Log
import com.sarthak.mynewsapplication.data.remote.api.NewsApi
import com.sarthak.mynewsapplication.domain.model.NewsResponse
import com.sarthak.mynewsapplication.domain.repository.NewsRepository
import com.sarthak.mynewsapplication.utils.FetchResult
import com.sarthak.mynewsapplication.utils.toNewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
): NewsRepository {

    override suspend fun getLatestNews(): Flow<FetchResult<NewsResponse>> = flow {
        emit(FetchResult.Loading())
        val newsResponseDto = try {
            newsApi.getLatestNews()
        } catch (e: IOException) {
            e.printStackTrace()
            emit(FetchResult.Error(message = "Oh! Could not load data."))
            return@flow
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(FetchResult.Error(message = "Oh! Could not load data."))
            return@flow
        } catch (e: Exception) {
            e.printStackTrace()
            emit(FetchResult.Error(message = "Oh! Could not load data."))
            return@flow
        }
        Log.d("NewsRepoImpl", newsResponseDto.toString())
        Log.d("NewsRepoImpl", newsResponseDto.toNewsResponse().toString())
        emit(FetchResult.Success(data = newsResponseDto.toNewsResponse()))
    }
}