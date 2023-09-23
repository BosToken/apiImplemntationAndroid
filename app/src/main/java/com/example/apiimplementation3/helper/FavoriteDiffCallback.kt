package com.example.apiimplementation3.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.apiimplementation3.data.database.Favorite

class FavoriteDiffCallback(private val oldFavorite: List<Favorite>, private val newFavoriteList: List<Favorite>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavorite.size
    override fun getNewListSize(): Int = newFavoriteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavorite[oldItemPosition].id == newFavoriteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldFavorite[oldItemPosition]
        val newNote = newFavoriteList[newItemPosition]
        return oldNote.username == newNote.username && oldNote.avatar == newNote.avatar
    }

}