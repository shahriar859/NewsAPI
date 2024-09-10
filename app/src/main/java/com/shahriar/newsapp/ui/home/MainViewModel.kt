package com.shahriar.newsapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahriar.newsapp.api.ApiClient
import com.shahriar.newsapp.api.Resource
import com.shahriar.newsapp.data.NewsResponse
import com.shahriar.newsapp.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    // Saving only NewsResponse instead of the Resource wrapper
    val newsResponse = MutableStateFlow<NewsResponse?>(null)

    // Separate variables to handle loading and error states
    val isLoading = MutableStateFlow(true)

    // Separate variables to handle loading and error states
    val errorMessage = MutableStateFlow<String?>(null)

    private val repository = PostRepository(ApiClient.api)


    init {
        getPostList()
    }

    private fun getPostList() {
        viewModelScope.launch {
            repository.getPosts().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        isLoading.value = false // Stop loading
                        newsResponse.value = resource.data // Set news data
//                        Log.d("Viewmodel",resource.data.articles?.size.toString())
                    }

                    is Resource.Error -> {
                        isLoading.value = false // Stop loading
                        errorMessage.value = resource.message // Set error message
                    }
                }
            }
        }
    }
}