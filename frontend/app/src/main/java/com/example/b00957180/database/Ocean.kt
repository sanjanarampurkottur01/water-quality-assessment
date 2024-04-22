package com.example.b00957180.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class representing an ocean record in the database.
 */
@Entity(tableName = "ocean")
data class Ocean(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val stationId: String,
    val date: String,
    val name: String,
    val description: String,
    val category: String,
    val amount: Double,
    val type: String
)
