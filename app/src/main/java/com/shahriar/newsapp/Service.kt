package com.shahriar.newsapp

import retrofit2.Call
import com.shahriar.newsapp.data.NewsResponse
import retrofit2.http.GET

interface PostService {
    @GET("everything?q=apple&from=2024-09-02&sortBy=popularity&apiKey=e95fec4ed187442e8743508eebaabde7")
    suspend fun getPosts(): NewsResponse
}