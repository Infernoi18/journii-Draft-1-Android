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
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        binding.tvRegister.setOnClickListener {
            Toast.makeText(this, "Register clicked", Toast.LENGTH_SHORT).show()
        }

        binding.btnLogin.setOnClickListener {

            val username = binding.etUsername.text?.toString()?.trim() ?: ""
            val password = binding.etPassword.text?.toString()?.trim() ?: ""

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}