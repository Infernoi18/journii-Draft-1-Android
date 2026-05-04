package com.example.journii

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.journii.data.Note
import com.example.journii.data.NoteDatabase
import com.example.journii.databinding.ActivityAddNoteBinding
import kotlinx.coroutines.launch

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private val types = listOf("Select Type", "Road", "Rail", "Air", "Ship", "Mix")

    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = NoteDatabase.getDatabase(this).noteDao()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerType.adapter = adapter


        noteId = intent.getIntExtra("note_id", -1)

        if (noteId != -1) {
            val title = intent.getStringExtra("note_title") ?: ""
            val content = intent.getStringExtra("note_content") ?: ""
            val categoryFull = intent.getStringExtra("note_category") ?: ""

            val parts = categoryFull.split("|")
            val category = parts[0]
            val type = if (parts.size > 1) parts[1] else "Select Type"

            binding.etTitle.setText(title)
            binding.etContent.setText(content)
            binding.etCategory.setText(category)

            val index = types.indexOf(type)
            binding.spinnerType.setSelection(if (index != -1) index else 0)
        } else {
            binding.spinnerType.setSelection(0)
        }

        binding.btnSave.setOnClickListener {

            val title = binding.etTitle.text.toString().trim()
            val content = binding.etContent.text.toString().trim()
            val category = binding.etCategory.text.toString().trim()
            val selected = binding.spinnerType.selectedItem.toString()

            val type = if (selected == "Select Type") "" else selected

            if (title.isEmpty() || content.isEmpty() || category.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val note = Note(
                id = if (noteId != -1) noteId else 0,
                title = title,
                content = content,
                category = "$category|$type"
            )

            lifecycleScope.launch {
                if (noteId != -1) {
                    dao.updateNote(note)
                    Toast.makeText(this@AddNoteActivity, "Updated", Toast.LENGTH_SHORT).show()
                } else {
                    dao.insertNote(note)
                    Toast.makeText(this@AddNoteActivity, "Saved", Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        }
    }
}