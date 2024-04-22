package com.example.b00957180.fragment

import android.content.Context
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
import com.example.b00957180.R

class SignUpFragment: Fragment() {
    // SharedPreferences
    private lateinit var sharedPreferences: SharedPreferences

    //UI elements
    private lateinit var potentialuserName: EditText
    private lateinit var usernameInfo: EditText
    private lateinit var passwordInfo: EditText
    private lateinit var buttonInfo: Button

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout XML file to create the view hierarchy for this fragment
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        // Initialize SharedPreferences
        sharedPreferences =
            requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Initialize UI elements
        buttonInfo = view.findViewById(R.id.createButton)
        potentialuserName = view.findViewById(R.id.profileNameEditText)
        usernameInfo = view.findViewById(R.id.usernameEditText)
        passwordInfo = view.findViewById(R.id.passwordEditText)

        // Button click listener
        buttonInfo.setOnClickListener {

            // Retrieve input data
            val profileName = potentialuserName.text.toString()
            val username = usernameInfo.text.toString()
            val password = passwordInfo.text.toString()

            // Validate sign up data
            if (isValidSignUp(profileName, username, password)) {
                // Save user details
                saveUserDetails(profileName, username, password)
                // Redirect to Login Fragment
                redirectToLoginFragment()
            }
        }

        return view
    }

    /**
     * Redirects to the Login Fragment.
     */
    private fun redirectToLoginFragment() {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, LoginFragment())

        // Add fragment to back stack to enable back navigation
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    /**
     * Saves user details to SharedPreferences.
     *
     * @param profileName The profile name entered by the user.
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     */
    private fun saveUserDetails(profileName: String, username: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("profileName", profileName)
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }

    /**
     * Validates sign up data.
     *
     * @param profileName The profile name entered by the user.
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return true if sign up data is valid, false otherwise.
     */
    private fun isValidSignUp(profileName: String, username: String, password: String): Boolean {
        if (profileName.isEmpty() || username.isEmpty() || password.isEmpty()) {

            // To handle empty fields error
            showError("All fields are required.")
            return false
        }

        // Regular expression for password validation
        val passwordPattern =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$".toRegex()

        //Password criteria check
        if (!password.matches(passwordPattern)) {
            val errorMessage = StringBuilder()

            //Password check for lowercase
            if (!password.any { it.isLowerCase() })
                errorMessage.append("Password must contain at least one lowercase letter.\n")

            //Password check for uppercase
            if (!password.any { it.isUpperCase() })
                errorMessage.append("Password must contain at least one uppercase letter.\n")

            //Password check for digit
            if (!password.any { it.isDigit() })
                errorMessage.append("Password must contain at least one digit.\n")

            //Password check for special symbol
            if (!password.any { it.isLetter() && !it.isDigit() })
                errorMessage.append("Password must contain at least one special character.\n")

            //Password check for length
            if (password.length < 8)
                errorMessage.append("Password must be at least 8 characters long.\n")
            showError(errorMessage.toString())
            return false
        }
        return true
    }

    /**
     * Displays error message.
     *
     * @param message The error message to be displayed.
     */
    private fun showError(message: String) {
        Toast.makeText(requireContext(), message.trim(), Toast.LENGTH_LONG).show()
    }
}