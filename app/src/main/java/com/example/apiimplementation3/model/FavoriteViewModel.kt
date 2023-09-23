package com.example.apiimplementation3.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.apiimplementation3.data.database.Favorite
import com.example.apiimplementation3.data.database.FavoriteDAO
import com.example.apiimplementation3.data.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers

class FavoriteViewModel(application: Application) : ViewModel()  {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorites()
}