package com.example.mywork.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "works")
data class WorkDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: Long,
    val counterparty: String,
    val worker: String,
    val description: String,
    val time: Long
)
