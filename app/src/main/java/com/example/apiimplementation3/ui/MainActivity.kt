package com.example.apiimplementation3.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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

        with(binding) {
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