package com.example.apiimplementation3.data.retrofit

import com.example.apiimplementation3.data.response.UserDetail
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
}
