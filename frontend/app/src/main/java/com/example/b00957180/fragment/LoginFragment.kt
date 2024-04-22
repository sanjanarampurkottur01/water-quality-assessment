package com.example.b00957180.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.b00957180.activity.LoadingActivity
import com.example.b00957180.R

class LoginFragment: Fragment() {
    // SharedPreferences to store user data
    private lateinit var sharedPreferences: SharedPreferences

    // Necessary UI elements
    private lateinit var usernameStore: EditText
    private lateinit var passwordStore: EditText
    private lateinit var loginaccessButton: Button
    private lateinit var signupaccessButton: Button

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Initialize SharedPreferences
        sharedPreferences =
            requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Initialize UI elements
        loginaccessButton = view.findViewById(R.id.loginButton)
        signupaccessButton = view.findViewById(R.id.signupButton)
        usernameStore = view.findViewById(R.id.usernameEditText)
        passwordStore = view.findViewById(R.id.passwordEditText)

        // Set username from SharedPreferences if available
        usernameStore.setText(sharedPreferences.getString("username", ""))

        // Login button click listener
        loginaccessButton.setOnClickListener {
            val userdetailsname = usernameStore.text.toString()
            val userdetailspassword = passwordStore.text.toString()

            // Validate credentials
            if (isValidCredentials(userdetailsname, userdetailspassword)) {

                /**
                 * Saves username to SharedPreferences.
                 *
                 * @param username The username to be saved.
                 */
                saveUsername(userdetailsname)

                // Redirect to ExpenseManager activity
                val intent = Intent(activity, LoadingActivity::class.java)
                intent.putExtra("username", userdetailsname)
                startActivity(intent)

            }
        }

        // Signup button click listener
        signupaccessButton.setOnClickListener {

            // Replace current fragment with SignUpFragment
            replaceFragment(SignUpFragment())
        }

        return view
    }

    // Function to save username to SharedPreferences
    private fun saveUsername(username: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putInt("loggedIn", 1)
        editor.apply()
    }

    /**
     * Replaces the current fragment with another fragment.
     *
     * @param fragment The fragment to replace the current fragment with.
     */
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)

        // Add fragment to back stack to enable back navigation
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    /**
     * Validates user credentials.
     *
     * @param username The username to be validated.
     * @param password The password to be validated.
     * @return true if the credentials are valid, false otherwise.
     */
    private fun isValidCredentials(username: String, password: String): Boolean {

        // Check if username and password are not empty
        if (username.isEmpty() || password.isEmpty()) {
            showError("Username and password are required.")
            return false
        }

        // Retrieve saved username and password from SharedPreferences
        val savedUsername = sharedPreferences.getString("username", "")
        val savedPassword = sharedPreferences.getString("password", "")

        // Check if input username and password match the saved ones
        return if (username == savedUsername && password == savedPassword) {
            true
        } else {
            showError("Invalid username or password.")
            false
        }
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to be displayed.
     */
    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}