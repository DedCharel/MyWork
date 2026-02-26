package com.example.mywork.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "organizations")
data class OrganizationDbModel(
    @PrimaryKey(autoGenerate = true) val organizationId: Long = 0,
    val name: String,
    val inn: String,
    val phone: String,
    val email: String,
    val address: String,
    val comments: String
)
