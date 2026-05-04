package com.example.journii

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journii.data.Note
import com.example.journii.data.NoteDatabase
import com.example.journii.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter
    private lateinit var dao: com.example.journii.data.NoteDao

    override fun onCreate(savedInstanceState: Bundle?) {

        // ✅ Install splash BEFORE super
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        var keepSplash = true

        lifecycleScope.launch {
            delay(1200)
            keepSplash = false
        }

        splashScreen.setKeepOnScreenCondition { keepSplash }

        // ✅ Normal setup
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)

        binding.btnLogout.setOnClickListener {
            prefs.edit()
                .putBoolean("isLoggedIn", false)
                .apply()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        dao = NoteDatabase.getDatabase(this).noteDao()

        adapter = NoteAdapter(
            emptyList(),
            onItemClick = { note ->
                val intent = Intent(this, AddNoteActivity::class.java)
                intent.putExtra("note_id", note.id)
                intent.putExtra("note_title", note.title)
                intent.putExtra("note_content", note.content)
                intent.putExtra("note_category", note.category)
                startActivity(intent)
            },
            onItemLongClick = { note ->
                showDeleteDialog(note)
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        dao.getAllNotes().observe(this, Observer { notes ->
            adapter.updateList(notes)
        })

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }
    }

    private fun showDeleteDialog(note: Note) {
        AlertDialog.Builder(this)
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Delete") { _, _ ->
                deleteNote(note)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteNote(note: Note) {
        lifecycleScope.launch {
            dao.deleteNote(note)
            Toast.makeText(this@MainActivity, "Deleted", Toast.LENGTH_SHORT).show()
        }
    }
}