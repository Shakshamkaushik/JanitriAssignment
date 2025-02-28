package com.example.janitriassignment.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.janitriassignment.model.UserDetails

@Dao
interface UserDetailsDao {
    @Insert
    suspend fun insertMessage(message: UserDetails)

    @Query("SELECT * FROM user_details ORDER BY id ASC")
    fun getAllMessages(): LiveData<List<UserDetails>>
}