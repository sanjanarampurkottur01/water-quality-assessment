package com.example.b00957180.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StationDao {
    @Insert
    fun insertAll(stations: List<Station>)

    @Query("SELECT DISTINCT stationId from station")
    fun getAllStationIds(): List<String>
}