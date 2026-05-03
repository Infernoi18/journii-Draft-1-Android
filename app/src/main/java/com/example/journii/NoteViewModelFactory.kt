package com.example.journii

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.journii.data.NoteDao

class NoteViewModelFactory(private val dao: NoteDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(dao) as T
    }
}