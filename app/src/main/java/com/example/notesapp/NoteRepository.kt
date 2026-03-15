package com.example.notesapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object NoteRepository {

    // FILES
    private const val NOTES_FILE = "notes.json"
    private const val TASKS_FILE = "tasks.json"

    private val gson = Gson()

    //------------------------------------
    // NOTE FUNCTIONS
    //------------------------------------


    suspend fun loadNotes(context: Context): List<Note> = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
        val file = File(context.filesDir, NOTES_FILE)
        if (!file.exists()) return@withContext emptyList()
        val json = file.readText()
        val type = object : TypeToken<List<Note>>() {}.type
        gson.fromJson(json, type)
    }

    suspend fun saveNotes(context: Context, notes: List<Note>) = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
        val json = gson.toJson(notes)
        File(context.filesDir, NOTES_FILE).writeText(json)
    }

    suspend fun saveSingle(context: Context, note: Note) = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
        val notes = loadNotes(context).toMutableList()
        val index = notes.indexOfFirst { it.id == note.id }
        if (index != -1) {
            notes[index] = note
            saveNotes(context, notes)
        }
    }

    //------------------------------------
    // GLOBAL TASK LIST FUNCTIONS
    //------------------------------------

    suspend fun loadTasks(context: Context): List<TaskItem> = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
        val file = File(context.filesDir, TASKS_FILE)
        if (!file.exists()) return@withContext emptyList()
        val json = file.readText()
        val type = object : TypeToken<List<TaskItem>>() {}.type
        gson.fromJson(json, type)
    }

    suspend fun saveTasks(context: Context, tasks: List<TaskItem>) = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
        val json = gson.toJson(tasks)
        File(context.filesDir, TASKS_FILE).writeText(json)
    }
}
