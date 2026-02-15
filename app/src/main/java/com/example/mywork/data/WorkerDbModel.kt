package com.example.mywork.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "workers")
data class WorkerDbModel(
    @PrimaryKey(autoGenerate = true) val workerId: Long = 0,
    val name: String
)
