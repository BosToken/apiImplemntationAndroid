package com.example.apiimplementation3.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.apiimplementation3.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import com.example.apiimplementation3.R
import com.example.apiimplementation3.data.response.UserDetail
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private var user : String? = null

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = intent.getStringExtra("login")
        setContentView(R.layout.activity_detail)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        detailUser(user.toString())
    }

    private fun detailUser(User : String){
        showLoading(true)
        val client = ApiConfig.getApiService().getDetailUser(User)
        client.enqueue(object : Callback<UserDetail> {
            override fun onResponse(
                call: Call<UserDetail>,
                response: Response<UserDetail>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val username : TextView = findViewById(R.id.tvName)
                        val photoProfile : ImageView = findViewById(R.id.tvImageProfile)
                        val follower : TextView = findViewById(R.id.tvFollower)
                        val following : TextView = findViewById(R.id.tvFollowing)

                        username.text = responseBody.login
                        Glide.with(this@DetailActivity).load(responseBody.avatarUrl).into(photoProfile)
                        follower.text = "${responseBody.followers.toString()} Followers"
                        following.text = "${responseBody.following.toString()} Following"
                    }
                } else {
                    Log.e(this.toString(), "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                showLoading(false)
                Log.e(this.toString(), "onFailure: ${t.message}")
            }
        })
    }

    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
        } else {
            findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
        }
    }
}