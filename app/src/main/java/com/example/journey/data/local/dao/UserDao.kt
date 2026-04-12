package com.example.journey.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.journey.data.local.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertuser(user: User)

    @Query("SELECT * FROM user_profile LIMIT 1")
    suspend fun getuser(): User?
}