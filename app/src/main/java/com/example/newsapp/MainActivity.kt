package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit.NewsService
import com.littlemango.stacklayoutmanager.StackLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var adapter: NewsAdapter
    val newsList = ArrayList<Article>()

    var page = 1
    var totalResults = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getNews()

        adapter = NewsAdapter(newsList)
        rvNews.adapter = adapter
        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(3000)
        layoutManager.setItemOffset(50);

        rvNews.layoutManager = layoutManager
        layoutManager.setItemChangedListener(object : StackLayoutManager.ItemChangedListener {
            override fun onItemChanged(position: Int) {
                if (totalResults > layoutManager.itemCount && position >= layoutManager.itemCount - 5){
                    page++
                    getNews()
                }
            }
        })


    }

    private fun getNews() {
        val news = NewsService.newsInstance.getHeadlines("in", page)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: retrofit2.Call<News>, response: Response<News>) {
                Log.d("MainActivity","Requesting data for page $page")
                val news = response.body()
                if (news != null) {
                    totalResults = news.totalResults
                    newsList.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: retrofit2.Call<News>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun setNews() {

    }
}