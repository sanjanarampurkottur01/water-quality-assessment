package com.example.b00957180.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.b00957180.R
import com.example.b00957180.adapter.ParameterAdapter
import com.example.b00957180.database.OceanDatabase
import com.example.b00957180.database.Station
import com.example.b00957180.model.Parameter
import com.example.b00957180.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var db: OceanDatabase
    var dataForAdapter = arrayOf<Parameter>()
    private lateinit var parameterAdapter: ParameterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerStation = findViewById<Spinner>(R.id.spinnerStation)
        val spinnerDate = findViewById<Spinner>(R.id.spinnerDate)
        val btnFetchParameter = findViewById<Button>(R.id.btnFetchParameter)
        val parameterList = findViewById<RecyclerView>(R.id.parametersList)

        db = Room.databaseBuilder(this, OceanDatabase::class.java, "ocean").build()

        parameterList.layoutManager = GridLayoutManager(this, 2)
        parameterAdapter = ParameterAdapter(dataForAdapter)

        parameterList.adapter = parameterAdapter

        // Load station IDs and sample dates into spinners
        loadStationIdsIntoSpinner(spinnerStation)
        loadSampleDatesIntoSpinner(spinnerDate)

        // Button click listener to fetch parameter
        btnFetchParameter.setOnClickListener {
            val selectedStationId = spinnerStation.selectedItem as String
            val selectedDate = spinnerDate.selectedItem as String

            Log.e("Data123", selectedDate)
            Log.e("Data123", selectedStationId)

            // Fetch parameter based on selected station ID, date, and parameter
            fetchParameter(selectedStationId, selectedDate)
        }
    }

    private fun loadStationIdsIntoSpinner(spinner: Spinner) {
        Thread {
            val stationIds = db.stationDao().getAllStationIds()
            Log.e("Station", stationIds.size.toString())

            runOnUiThread {
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, stationIds)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }.start()
    }

    private fun loadSampleDatesIntoSpinner(spinner: Spinner) {
        Thread {
            val sampleDates = db.dateDao().getAllDates()
            Log.e("Dates", sampleDates.size.toString())

            runOnUiThread {
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sampleDates)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }.start()
    }

    private fun fetchParameter(stationId: String, date: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val parameterQuery = "SELECT DISTINCT parameter, result, reporting_limit FROM ocean.lab_results WHERE station_id = $stationId AND sample_date = '$date'"
            val parameterRequestBody = mapOf("query" to parameterQuery)

            val parameterCall = ApiClient.apiService.fetchData(parameterRequestBody)

            try {
                val response = parameterCall.execute()

                if (response.isSuccessful) {
                    val fetchDataResponse = response.body()

                    if (fetchDataResponse != null) {
                        Log.e("Test123", fetchDataResponse.data.toString())
                        // Extract list of dates from the response data
                        val newParameters =
                            fetchDataResponse.data.map { Parameter(it[0], it[1], it[2]) }.toTypedArray()

                        // Now that you have the new data, update the adapter and RecyclerView
                        launch(Dispatchers.Main) {
                            parameterAdapter.updateData(newParameters)
                        }
                    }
                } else {
                    // Handle unsuccessful response
                    println("Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
