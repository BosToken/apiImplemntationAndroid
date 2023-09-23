package com.example.apiimplementation3.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE username = :searchQuery")
    fun delete(searchQuery: String)
//    @Delete
//    fun delete(favorite: Favorite)

    @Query("SELECT * FROM favorite ORDER BY id ASC")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE username = :searchQuery")
    fun search(searchQuery: String): LiveData<List<Favorite>>
}