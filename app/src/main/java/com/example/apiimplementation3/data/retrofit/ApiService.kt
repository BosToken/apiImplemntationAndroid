package com.example.apiimplementation3.data.retrofit

import com.example.apiimplementation3.data.response.UserDetail
import com.example.apiimplementation3.data.response.UserFollow
import com.example.apiimplementation3.data.response.UserFollowItem
import com.example.apiimplementation3.data.response.UserResponse
import retrofit2.http.*
import retrofit2.Call

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{user}")
    fun getDetailUser(
        @Path("user") user : String
    ):Call<UserDetail>

    @GET("users/{user}/followers")
    fun getFollower(
        @Path("user") user: String
    ):Call<List<UserFollowItem>>

    @GET("users/{user}/following")
    fun getFollowing(
        @Path("user") user: String
    ):Call<List<UserFollowItem>>
}
