package com.example.apiimplementation3.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiimplementation3.data.response.ItemsItem
import com.example.apiimplementation3.data.response.UserResponse
import com.example.apiimplementation3.data.retrofit.ApiConfig
import com.example.apiimplementation3.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import androidx.recyclerview.widget.RecyclerView
import com.example.apiimplementation3.R
import com.example.apiimplementation3.data.database.SettingPreferences
import com.example.apiimplementation3.data.database.dataStore
import com.example.apiimplementation3.helper.SettingModelFactory
import com.example.apiimplementation3.model.SettingViewModel
import com.example.apiimplementation3.ui.adapter.UserListAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: RecyclerView

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listAdapter = findViewById(R.id.rvReview)
        listAdapter.setHasFixedSize(true)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, SettingModelFactory(pref)).get(
            SettingViewModel::class.java
        )
        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        with(binding) {
            searchBar.inflateMenu(R.menu.search_menu);
            searchBar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menuFavorite -> {
                        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.menuTheme -> {
                        val intent = Intent(this@MainActivity, ThemeActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }

            }
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    findUser(searchView.text.toString())
                    false
                }

        }
        supportActionBar?.hide()

        showList()
    }
    private fun showList(){
        val layoutManager = LinearLayoutManager(binding.rvReview.context)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(binding.rvReview.context, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)
    }

    private fun findUser(Username : String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getUser(Username)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setUserData(responseBody.items)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setUserData(consumerReviews: List<ItemsItem>) {
        val adapter = UserListAdapter()
        adapter.submitList(consumerReviews)
        binding.rvReview.adapter = adapter
    }

    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}