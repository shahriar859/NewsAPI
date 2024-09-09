package com.shahriar.newsapp.repository

import android.util.Log
import com.shahriar.newsapp.api.Resource
import com.shahriar.newsapp.PostService
import com.shahriar.newsapp.data.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PostRepository(private val api: PostService) {
    suspend fun getPosts(): Flow<Resource<NewsResponse>> = flow {
        Log.d("Repository", "getPosts")
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.getPosts() // Make the network request
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)


}