package com.example.mywork.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkersDao {

    @Query("SELECT * FROM workers ORDER BY name DESC")
    fun getAllWorkers(): Flow<List<WorkerDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorker(workerDao: WorkersDao)
}