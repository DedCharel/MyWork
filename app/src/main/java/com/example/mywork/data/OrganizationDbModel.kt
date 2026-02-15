package com.example.mywork.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "organizations")
data class OrganizationDbModel(
    @PrimaryKey(autoGenerate = true) val organizationId: Long = 0,
    val name: String
)
