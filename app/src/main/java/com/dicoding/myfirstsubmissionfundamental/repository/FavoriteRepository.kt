package com.dicoding.myfirstsubmissionfundamental.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.myfirstsubmissionfundamental.database.FavoriteDao
import com.dicoding.myfirstsubmissionfundamental.database.FavoriteRoomDatabase
import com.dicoding.myfirstsubmissionfundamental.database.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavDao: FavoriteDao

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavDao = db.favoriteDao()
    }
    fun getAllNotes(): LiveData<List<FavoriteUser>> = mFavDao.getAllNotes()

    fun insert(note: FavoriteUser) {
        executorService.execute { mFavDao.insert(note) }
    }
    fun delete(note: FavoriteUser) {
        executorService.execute { mFavDao.delete(note) }
    }
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = mFavDao.getFavoriteUserByUsername(username)
}