package com.example.journii

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.journii.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)

        binding.btnRegister.setOnClickListener {

            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // ✅ FIX: prevent empty save
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            prefs.edit()
                .putString("username", username)
                .putString("password", password)
                .apply()

            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}