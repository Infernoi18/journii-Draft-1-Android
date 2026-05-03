package com.example.journii

import androidx.lifecycle.*
import com.example.journii.data.Note
import com.example.journii.data.NoteDao
import kotlinx.coroutines.launch

class NoteViewModel(private val dao: NoteDao) : ViewModel() {

    val allNotes: LiveData<List<Note>> = dao.getAllNotes()

    fun insert(note: Note) = viewModelScope.launch {
        dao.insertNote(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        dao.deleteNote(note)
    }
}