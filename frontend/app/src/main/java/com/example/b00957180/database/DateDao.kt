package com.example.b00957180.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DateDao {
    @Insert
    fun insertAll(dates : List<Date>)

    @Query("SELECT DISTINCT date from date")
    fun getAllDates(): List<String>
}