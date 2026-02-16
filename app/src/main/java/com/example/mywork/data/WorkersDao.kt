package com.example.mywork.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkersDao {

    @Transaction
    @Query("SELECT * FROM workers ORDER BY name DESC")
    fun getAllWorkers(): Flow<List<WorkerDbModel>>

    @Transaction
    @Query("SELECT * FROM workers WHERE workerId == :workerId")
    suspend fun getWorker(workerId: Long): WorkerDbModel

    @Transaction
    @Query("DELETE FROM workers WHERE workerId == :workerId")
    suspend fun deleteWorker(workerId: Long)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorker(worker: WorkerDbModel)

}