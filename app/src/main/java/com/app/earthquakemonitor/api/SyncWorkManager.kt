package com.app.earthquakemonitor.api

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.earthquakemonitor.database.getDatabase
import com.app.earthquakemonitor.main.MainRepository

class SyncWorkManager(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {
    companion object{
        const val WORK_NAME = "SyncWorkManager"
    }

    private val database = getDatabase(context)
    private val repository = MainRepository(database)

    override suspend fun doWork(): Result {
       repository.fetchEarthquakes(true)
        return Result.success()
    }
}