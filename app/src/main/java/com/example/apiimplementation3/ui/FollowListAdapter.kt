package com.example.apiimplementation3.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apiimplementation3.R
import com.example.apiimplementation3.data.response.UserFollowItem

class FollowListAdapter(private val listHero: List<UserFollowItem>) : RecyclerView.Adapter<FollowListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (avatar, username) = listHero[position]
        holder.tvText.text = username
        Glide.with(holder.itemView.context).load(avatar).into(holder.imgPhoto)
    }

    override fun getItemCount(): Int = listHero.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.tvImageProfile)
        val tvText: TextView = itemView.findViewById(R.id.tvText)
    }
}

