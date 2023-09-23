package com.example.apiimplementation3.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apiimplementation3.model.FavoriteViewModel
import com.example.apiimplementation3.ui.insert.FavoriteAddUpdateViewModel

class FavoriteModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory()  {
    companion object {
        @Volatile
        private var INSTANCE: FavoriteModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): FavoriteModelFactory {
            if (INSTANCE == null) {
                synchronized(FavoriteModelFactory::class.java) {
                    INSTANCE = FavoriteModelFactory(application)
                }
            }
            return INSTANCE as FavoriteModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteAddUpdateViewModel::class.java)) {
            return FavoriteAddUpdateViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}