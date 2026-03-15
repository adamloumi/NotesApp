package com.example.notesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.content.Context

class NotesViewModel : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    fun loadNotes(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val loaded = NoteRepository.loadNotes(context)
            _notes.value = loaded
        }
    }

    fun refreshNotes(context: Context) {
        loadNotes(context)
    }
}
