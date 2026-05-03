package com.example.journii

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.journii.data.Note
import com.example.journii.data.NoteDatabase
import com.example.journii.databinding.ActivityAddNoteBinding
import kotlinx.coroutines.launch

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = NoteDatabase.getDatabase(this).noteDao()

        binding.btnSave.setOnClickListener {

            val title = binding.etTitle.text.toString().trim()
            val content = binding.etContent.text.toString().trim()
            val category = binding.etCategory.text.toString().trim()

            if (title.isEmpty() || content.isEmpty() || category.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val note = Note(
                title = title,
                content = content,
                category = category
            )

            lifecycleScope.launch {
                dao.insertNote(note)
                Toast.makeText(this@AddNoteActivity, "Saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}