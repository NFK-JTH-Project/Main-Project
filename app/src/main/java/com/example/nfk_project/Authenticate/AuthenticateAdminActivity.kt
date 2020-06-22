package com.example.nfk_project.Authenticate

import android.app.Activity
import android.os.Bundle
import android.view.View.GONE
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.nfk_project.R
import kotlinx.android.synthetic.main.toolbar.view.*

class AuthenticateAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticate_admin)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.britainBtn.visibility = GONE
        toolbar.swedenBtn.visibility = GONE

        val continueButton = findViewById<Button>(R.id.continue_button)
        val passwordInput = findViewById<EditText>(R.id.password_input)

        toolbar.backBtn.setOnClickListener {
            finish()
        }

        val adminPassword = "Rabarber04"

        continueButton.setOnClickListener {
            val pwInput = passwordInput.text.toString()

            if (pwInput.isNotEmpty()) {
                if (pwInput == adminPassword) {
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    passwordInput.text.clear()
                }
            }
        }
    }
}