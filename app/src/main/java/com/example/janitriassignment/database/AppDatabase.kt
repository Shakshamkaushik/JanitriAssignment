package com.example.janitriassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.janitriassignment.dao.UserDetailsDao
import com.example.janitriassignment.model.UserDetails


@Database(entities = [UserDetails::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun userChatDao(): UserDetailsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_details_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}