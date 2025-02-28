package com.example.janitriassignment.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_details")
data class UserDetails(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sysBp: String,
    val diaBp: String,
    val weight: String,
    val babyKicks: String,
    val time: String,
)
