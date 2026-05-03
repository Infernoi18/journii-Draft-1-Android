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

        adapter = NoteAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        val dao = NoteDatabase.getDatabase(this).noteDao()

        // 🔥 THIS IS THE IMPORTANT PART
        dao.getAllNotes().observe(this, Observer { notes ->
            adapter.updateList(notes)
        })

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }
    }
}