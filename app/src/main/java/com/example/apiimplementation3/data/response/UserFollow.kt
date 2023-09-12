package com.example.apiimplementation3.data.response

import com.google.gson.annotations.SerializedName

data class UserFollow(

	@field:SerializedName("UserFollow")
	val userFollow: List<UserFollowItem>
)