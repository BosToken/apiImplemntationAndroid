package com.example.apiimplementation3.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apiimplementation3.R
import com.example.apiimplementation3.data.response.UserFollowItem
import com.example.apiimplementation3.data.retrofit.ApiConfig
import com.example.apiimplementation3.databinding.FragmentFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var rvFolllowing: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val arg = arguments?.getString("user")

        rvFolllowing = binding.root.findViewById(R.id.rvFolllowing)
        rvFolllowing.setHasFixedSize(true)

        findFollowing(arg.toString())
        return binding.root
    }

    private fun findFollowing(user : String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollowing(user)
        client.enqueue(object : Callback<List<UserFollowItem>> {
            override fun onResponse(
                call: Call<List<UserFollowItem>>,
                response: Response<List<UserFollowItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        showList(responseBody)
                    }
                } else {
                }
            }
            override fun onFailure(call: Call<List<UserFollowItem>>, t: Throwable) {
                showLoading(false)
                Log.d("Error", "onFailure: ${t.message}")
            }

        })
    }

    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showList(userFollow: List<UserFollowItem>) {
        rvFolllowing.layoutManager = LinearLayoutManager(binding.rvFolllowing.context)
        val listuserAdapter = FollowListAdapter(userFollow)
        rvFolllowing.adapter = listuserAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FollowingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}