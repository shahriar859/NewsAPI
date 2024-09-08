package com.shahriar.newsapp.ui.home

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shahriar.newsapp.R
import com.shahriar.newsapp.RetrofitInstance
import com.shahriar.newsapp.data.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostNewsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_news)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        newsAdapter = NewsAdapter(emptyList())
        recyclerView.adapter = newsAdapter

        fetchNewsArticles()
    }

    private fun fetchNewsArticles() {
        RetrofitInstance.api.getNewsArticle().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val articles = response.body()?.articles?.filterNotNull() ?: emptyList()
                    newsAdapter.updateArticles(articles)
                } else {
                    Toast.makeText(this@PostNewsActivity, "Failed to load news", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e("PostNewsActivity", "Error: ${t.message}")
                Toast.makeText(this@PostNewsActivity, "Error fetching news", Toast.LENGTH_SHORT).show()
            }
        })
    }
}