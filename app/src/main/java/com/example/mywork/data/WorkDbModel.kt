package com.example.mywork.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "works",
    foreignKeys = [
        ForeignKey(
            entity = OrganizationDbModel::class,
            parentColumns = ["organizationId"],
            childColumns = ["organizationId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WorkerDbModel::class,
            parentColumns = ["workerId"],
            childColumns = ["workerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("organisationId"), Index("workerId")])
data class WorkDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: Long,
    val organizationId: Long,
    val workerId: Long,
    val description: String,
    val time: Long
)

