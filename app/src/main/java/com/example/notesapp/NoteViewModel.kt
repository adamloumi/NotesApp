package com.example.notesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.content.Context

class NoteViewModel : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    fun loadNotes(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val loaded = NoteRepository.loadNotes(context)
            _notes.value = loaded
        }
    }

    fun deleteNote(context: Context, noteId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = _notes.value.filter { it.id != noteId }
            NoteRepository.saveNotes(context, updated)
            _notes.value = updated
        }
    }

    fun pinNote(context: Context, noteId: Long, pin: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = _notes.value.map {
                if (it.id == noteId) it.copy(pinned = pin) else it
            }
            NoteRepository.saveNotes(context, updated)
            _notes.value = updated
        }
    }
}
