package com.example.apiimplementation3.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.apiimplementation3.data.database.Favorite
import com.example.apiimplementation3.data.database.FavoriteDAO
import com.example.apiimplementation3.data.database.FavoriteRoomDatabase
import java.util.concurrent.*

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDAO()
    }
    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorites()

    fun insert(favorite: Favorite) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }
    fun search(username: String): LiveData<List<Favorite>> {
        executorService.execute { mFavoriteDao.search(username) }
        return mFavoriteDao.search(username)
    }
    fun delete(username: String) {
        executorService.execute { mFavoriteDao.delete(username) }
    }
}