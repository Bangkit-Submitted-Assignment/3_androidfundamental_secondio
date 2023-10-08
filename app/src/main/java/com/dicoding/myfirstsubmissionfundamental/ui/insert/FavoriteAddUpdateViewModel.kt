package com.dicoding.myfirstsubmissionfundamental.ui.insert

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.myfirstsubmissionfundamental.database.FavoriteUser
import com.dicoding.myfirstsubmissionfundamental.repository.FavoriteRepository

class FavoriteAddUpdateViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: FavoriteRepository = FavoriteRepository(application)
    fun insert(note: FavoriteUser) {
        mNoteRepository.insert(note)
    }
    fun delete(note: FavoriteUser) {
        mNoteRepository.delete(note)
    }
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = mNoteRepository.getFavoriteUserByUsername(username)
}