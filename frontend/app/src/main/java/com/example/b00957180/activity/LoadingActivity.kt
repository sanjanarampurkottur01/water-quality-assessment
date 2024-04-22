package com.example.b00957180.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.b00957180.R
import com.example.b00957180.database.Date
import com.example.b00957180.database.OceanDatabase
import com.example.b00957180.database.Station
import com.example.b00957180.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoadingActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var loadBtn: Button

    private lateinit var db: OceanDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loading)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = Room.databaseBuilder(this, OceanDatabase::class.java, "ocean").build()

        progressBar = findViewById(R.id.loading_progress)
        loadBtn = findViewById(R.id.load_btn)

        progressBar.visibility = View.INVISIBLE

        loadBtn.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            loadDataIntoDb()
        }
    }

    private fun loadDataIntoDb() {
        GlobalScope.launch(Dispatchers.IO) {
            val query = "SELECT DISTINCT sample_date FROM ocean.lab_results"
            val requestBody = mapOf("query" to query)

            val call = ApiClient.apiService.fetchData(requestBody)

            try {
                val response = call.execute()

                if (response.isSuccessful) {
                    val fetchDataResponse = response.body()

                    if (fetchDataResponse != null) {
                        // Extract list of dates from the response data
                        val datesList = fetchDataResponse.data.flatten()

                        val listOfDates = datesList.map { Date(it) }

                        // Now datesList contains a flattened list of dates
                        // You can further process this list as needed
                        db.dateDao().insertAll(listOfDates)
                    }
                } else {
                    // Handle unsuccessful response
                    println("Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val stationQuery = "SELECT DISTINCT station_id FROM ocean.lab_results"
            val stationRequestBody = mapOf("query" to stationQuery)

            val stationCall = ApiClient.apiService.fetchData(stationRequestBody)

            try {
                val response = stationCall.execute()

                if (response.isSuccessful) {
                    val fetchDataResponse = response.body()

                    if (fetchDataResponse != null) {
                        // Extract list of dates from the response data
                        val datesList = fetchDataResponse.data.flatten()

                        val listOfStations = datesList.map { Station(it) }

                        // Now datesList contains a flattened list of dates
                        // You can further process this list as needed
                        db.stationDao().insertAll(listOfStations)
                    }
                } else {
                    // Handle unsuccessful response
                    println("Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            launch (Dispatchers.Main) {
                progressBar.visibility = View.GONE
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        }.start()
    }
}