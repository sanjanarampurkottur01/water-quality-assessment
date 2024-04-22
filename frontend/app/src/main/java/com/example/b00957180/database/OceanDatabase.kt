package com.example.b00957180.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room database class for managing ocean records.
 */
@Database(entities = [Ocean::class, Date::class, Station::class], version = 2)
abstract class OceanDatabase : RoomDatabase() {
    abstract fun oceanDao(): OceanDao
    abstract fun dateDao(): DateDao
    abstract fun stationDao(): StationDao
}
