package com.example.newsapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_article.view.*

class NewsAdapter(val newsList: ArrayList<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = newsList[position]
        holder.itemView.apply {

            Glide.with(context).load(article.urlToImage).into(ivArticleImage)
            tvArticleTitle.text = article.title
            tvArticleAuthor.text = if (article.author != null) article.author else "Unknown"
        }
        holder.itemView.setOnClickListener {
            Intent(holder.itemView.context, DetailActivity::class.java)
                .also {
                    it.putExtra("URL", article.url)
                    holder.itemView.context.startActivity(it)
                }
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

}