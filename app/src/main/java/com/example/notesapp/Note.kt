package com.example.notesapp


data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val timestamp: String,
    val pinned: Boolean = false,
)
