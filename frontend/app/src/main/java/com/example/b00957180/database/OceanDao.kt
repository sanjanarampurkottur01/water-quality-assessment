package com.example.b00957180.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Data Access Object (DAO) interface for managing ocean records in the database.
 */
@Dao
interface OceanDao {

    /**
     * Retrieves all ocean records from the database.
     *
     * @return A list of all ocean records in the database.
     */
    @Query("SELECT * FROM ocean")
    fun getAll(): List<Ocean>

    /**
     * Retrieves an ocean record by its name from the database.
     *
     * @param name The name of the ocean record to retrieve.
     * @return The ocean record with the specified name.
     */
    @Query("SELECT * FROM ocean WHERE name = :station_name")
    fun loadByName(station_name: String): Ocean

    @Query("SELECT * FROM ocean WHERE name = :station_name AND date = :date")
    fun loadByNameAndDate(station_name: String, date: String): Ocean

    /**
     * Inserts one or more ocean records into the database.
     *
     * @param oceans The ocean records to insert.
     */
    @Insert
    fun insertAll(vararg oceans: Ocean)

    /**
     * Deletes an ocean record from the database.
     *
     * @param ocean The ocean record to delete.
     */
    @Delete
    fun delete(ocean: Ocean)
}
