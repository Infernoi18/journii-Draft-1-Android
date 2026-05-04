package com.example.journii

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.journii.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)

        // 🔥 AUTO REDIRECT (IMPORTANT)
        val isLoggedIn = prefs.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔹 LOGIN BUTTON
        binding.btnLogin.setOnClickListener {

            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val savedUsername = prefs.getString("username", "")
            val savedPassword = prefs.getString("password", "")

            if (username == savedUsername && password == savedPassword) {

                // 🔥 SAVE LOGIN STATE
                prefs.edit()
                    .putBoolean("isLoggedIn", true)
                    .apply()

                startActivity(Intent(this, MainActivity::class.java))
                finish()

            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        // 🔹 GO TO REGISTER
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}