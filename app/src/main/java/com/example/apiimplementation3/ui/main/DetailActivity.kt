package com.example.apiimplementation3.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.apiimplementation3.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import com.example.apiimplementation3.R
import com.example.apiimplementation3.data.database.Favorite
import com.example.apiimplementation3.data.response.UserDetail
import com.example.apiimplementation3.databinding.ActivityDetailBinding
import com.example.apiimplementation3.helper.FavoriteModelFactory
import com.example.apiimplementation3.ui.adapter.SectionsPagerAdapter
import com.example.apiimplementation3.ui.insert.FavoriteAddUpdateViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private var activityDetail: ActivityDetailBinding? = null
    private var user : String? = null
    private val sectionsPagerAdapter = SectionsPagerAdapter(this)

    private var userFavorite : String? = null
    private var avatarFavorite : String? = null

    private var favorite: Favorite? = null
    private var isFavorite: Boolean? = false
    private lateinit var favAddUpdateViewModel: FavoriteAddUpdateViewModel
    private val binding get() = activityDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetail = ActivityDetailBinding.inflate(layoutInflater)
        user = intent.getStringExtra("login")
        setContentView(binding?.root)

        favAddUpdateViewModel = obtainViewModel(this@DetailActivity)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        sectionsPagerAdapter.getUser(user.toString())
        detailUser(user.toString())

        favorite = intent.getParcelableExtra(EXTRA_NOTE)

        favAddUpdateViewModel.search(user.toString()).observe(this@DetailActivity, { favorites ->
            if (favorites != null && favorites.isNotEmpty()){
                changeIconFav(true)
                this.isFavorite = true
            }
            else{
                changeIconFav(false)
                this.isFavorite = false
            }
            binding?.fab?.setOnClickListener{
                when{
                    isFavorite == true->{
                        favAddUpdateViewModel.delete(user.toString())
                    }
                    else->{
                        favAddUpdateViewModel.insert(Favorite(username = userFavorite, avatar = avatarFavorite))
                    }
                }
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddUpdateViewModel {
        val factory = FavoriteModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteAddUpdateViewModel::class.java)
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
                        val username : TextView = findViewById(R.id.tvUsername)
                        val name : TextView = findViewById(R.id.tvName)
                        val photoProfile : ImageView = findViewById(R.id.tvImageProfile)
                        val follower : TextView = findViewById(R.id.tvFollower)
                        val following : TextView = findViewById(R.id.tvFollowing)

                        username.text = responseBody.login
                        name.text = responseBody.name
                        Glide.with(this@DetailActivity).load(responseBody.avatarUrl).into(photoProfile)
                        follower.text = "${responseBody.followers} Followers"
                        following.text = "${responseBody.following} Following"

                        setFavoriteData(responseBody.login, responseBody.avatarUrl)

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

    fun setFavoriteData(username : String, avatar : String){
        userFavorite = username
        avatarFavorite = avatar
    }

    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
        } else {
            findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
        }
    }

    fun changeIconFav(isFavorite: Boolean){
        when {
            isFavorite -> {
                this.isFavorite = isFavorite
                binding?.fab?.setImageResource(R.drawable.baseline_favorite_24)
            }
            else -> {
                this.isFavorite = isFavorite
                binding?.fab?.setImageResource(R.drawable.baseline_favorite_border_24)
            }
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
}
