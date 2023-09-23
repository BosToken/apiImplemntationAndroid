package com.example.apiimplementation3.ui.insert

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apiimplementation3.data.database.Favorite
import com.example.apiimplementation3.data.repository.FavoriteRepository

class FavoriteAddUpdateViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }
    fun search(username: String): LiveData<List<Favorite>>{
        return mFavoriteRepository.search(username)
    }
    fun delete(username: String) {
        mFavoriteRepository.delete(username)
    }
}
