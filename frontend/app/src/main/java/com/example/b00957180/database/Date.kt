package com.example.b00957180.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "date")
data class Date(
    @PrimaryKey
    val date: String
)