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

    fun loadNotes(context: Context): List<Note> {
        val file = File(context.filesDir, NOTES_FILE)
        if (!file.exists()) return emptyList()
        val json = file.readText()

        val type = object : TypeToken<List<Note>>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveNotes(context: Context, notes: List<Note>) {
        val json = gson.toJson(notes)
        File(context.filesDir, NOTES_FILE).writeText(json)
    }

    fun saveSingle(context: Context, note: Note) {
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

    fun loadTasks(context: Context): List<TaskItem> {
        val file = File(context.filesDir, TASKS_FILE)
        if (!file.exists()) return emptyList()
        val json = file.readText()

        val type = object : TypeToken<List<TaskItem>>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveTasks(context: Context, tasks: List<TaskItem>) {
        val json = gson.toJson(tasks)
        File(context.filesDir, TASKS_FILE).writeText(json)
    }
}
