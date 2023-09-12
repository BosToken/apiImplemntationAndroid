package com.example.apiimplementation3.data.response

import com.google.gson.annotations.SerializedName

data class UserFollowItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val login: String
)