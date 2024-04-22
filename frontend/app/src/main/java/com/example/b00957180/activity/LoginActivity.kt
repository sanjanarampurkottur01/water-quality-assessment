package com.example.b00957180.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.b00957180.R
import com.example.b00957180.fragment.LoginFragment
import com.example.b00957180.database.OceanDatabase

class LoginActivity : AppCompatActivity() {
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     * then this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState}.
     * Otherwise it is null.
     */
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize the login fragment in the fragment container
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, LoginFragment())
            .commit()

        val db = Room.databaseBuilder(
            applicationContext,

            // Initialize the expense database
            OceanDatabase::class.java, "database-name"
        ).build()
    }
}