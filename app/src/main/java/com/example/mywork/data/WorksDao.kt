package com.example.mywork.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface WorksDao {

    @Transaction
    @Query("SELECT * FROM works ORDER BY date DESC")
    fun getAllWorks(): Flow<List<WorkWithReferences>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWork(workDbModel: WorkDbModel)

    @Transaction
    @Query("DELETE FROM works WHERE id == :workId")
    suspend fun deleteWork(workId: Int)

    @Transaction
    @Query("SELECT * FROM works WHERE id == :workId")
    suspend fun getWork(workId: Int): WorkDbModel
}