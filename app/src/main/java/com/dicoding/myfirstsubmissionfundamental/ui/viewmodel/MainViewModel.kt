package com.dicoding.myfirstsubmissionfundamental.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.myfirstsubmissionfundamental.database.FavoriteUser
import com.dicoding.myfirstsubmissionfundamental.repository.FavoriteRepository

class MainViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: FavoriteRepository = FavoriteRepository(application)
    fun getAllNotes(): LiveData<List<FavoriteUser>> = mNoteRepository.getAllNotes()
}