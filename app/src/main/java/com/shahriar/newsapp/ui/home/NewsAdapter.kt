package com.shahriar.newsapp.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shahriar.newsapp.R
import com.shahriar.newsapp.data.Article

class NewsAdapter(
    private var articleList: List<Article>)
    :RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val articleImage: ImageView = itemView.findViewById(R.id.articleImage)
        val articleTitle: TextView = itemView.findViewById(R.id.articleTitle)
        val articleDescription: TextView = itemView.findViewById(R.id.articleDescription)
        val articleAuthor: TextView = itemView.findViewById(R.id.articleAuthor)
        val publishedAt: TextView = itemView.findViewById(R.id.publishedAt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articleList[position]

        holder.apply {
            articleTitle.text = article.title
            articleDescription.text = article.description
            articleAuthor.text = article.author ?: "Unknown"
            articleImage.load(article.urlToImage) {
                crossfade(true)
                placeholder(R.drawable.placeholder)
            }
            publishedAt.text = article.publishedAt
        }

    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateArticles(newArticles: List<Article>) {
        articleList = newArticles
        notifyDataSetChanged()
    }
}