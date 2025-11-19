package com.example.notesapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object NoteRepository {

    private const val FILE_NAME = "notes.json"
    private val gson = Gson()

    fun saveNotes(context: Context, notes: List<Note>) {
        val json = gson.toJson(notes)
        val file = File(context.filesDir, FILE_NAME)
        file.writeText(json)
    }

    fun loadNotes(context: Context): List<Note> {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) return emptyList()
        val json = file.readText()

        val type = object : TypeToken<List<Note>>() {}.type
        return gson.fromJson(json, type)
    }
}
