package com.example.journii

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journii.data.NoteDatabase
import com.example.journii.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔥 SharedPreferences
        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)

        adapter = NoteAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        val dao = NoteDatabase.getDatabase(this).noteDao()

        dao.getAllNotes().observe(this, Observer { notes ->
            adapter.updateList(notes)
        })

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }

        // 🔴 LOGOUT BUTTON CLICK
        binding.btnLogout.setOnClickListener {

            prefs.edit()
                .putBoolean("isLoggedIn", false)
                .apply()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}