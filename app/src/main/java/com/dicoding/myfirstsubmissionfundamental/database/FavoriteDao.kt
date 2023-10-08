package com.dicoding.myfirstsubmissionfundamental.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(fav: FavoriteUser)
    @Delete
    fun delete(fav: FavoriteUser)
    @Query("SELECT * from FavoriteUser ORDER BY username ASC")
    fun getAllNotes(): LiveData<List<FavoriteUser>>
    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>

//    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
//    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>
}