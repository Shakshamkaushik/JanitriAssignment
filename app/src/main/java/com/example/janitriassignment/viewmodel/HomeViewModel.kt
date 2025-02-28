package com.example.janitriassignment.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.janitriassignment.dao.UserDetailsDao
import com.example.janitriassignment.database.AppDatabase
import com.example.janitriassignment.model.UserDetails
import com.example.janitriassignment.worker.NotifyWorker
import java.util.concurrent.TimeUnit

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context: Context = application.applicationContext

    private val _data = MutableLiveData<List<UserDetails>>()
    val data: LiveData<List<UserDetails>> = _data

    private val messageDao: UserDetailsDao = AppDatabase.getDatabase(context).userChatDao()

    init {
        messageDao.getAllMessages().observeForever {
            _data.value = it
        }
    }
    private val workManager = WorkManager.getInstance(application)

    fun scheduleNotification() {
        val workRequest = PeriodicWorkRequestBuilder<NotifyWorker>(5, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .build()

        workManager.enqueueUniquePeriodicWork(
            "notification_work",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    suspend fun insertData( userChat: UserDetails) {
        messageDao.insertMessage(userChat)
    }
}