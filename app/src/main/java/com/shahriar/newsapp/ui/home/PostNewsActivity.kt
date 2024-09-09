package com.shahriar.newsapp.ui.home

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shahriar.newsapp.R
import kotlinx.coroutines.launch

class PostNewsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_news)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        progressBar = findViewById(R.id.progressBar)
        newsAdapter = NewsAdapter(emptyList())
        recyclerView.adapter = newsAdapter

        handleLoading()

        lifecycleScope.launch {
            viewModel.postResponse.collect { response ->
                if (response != null) {
                    val articles = response.articles?.filterNotNull() ?: emptyList()
                    newsAdapter.updateArticles(articles)
                }
            }
        }

    }

    private fun handleLoading() {
        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

}