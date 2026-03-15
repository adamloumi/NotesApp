package com.example.notesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.content.Context

class TasksViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<TaskItem>>(emptyList())
    val tasks: StateFlow<List<TaskItem>> = _tasks

    fun loadTasks(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val loaded = NoteRepository.loadTasks(context)
            _tasks.value = loaded
        }
    }

    fun addTask(context: Context, task: TaskItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = _tasks.value + task
            NoteRepository.saveTasks(context, updated)
            _tasks.value = updated
        }
    }

    fun updateTask(context: Context, task: TaskItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = _tasks.value.map {
                if (it.text == task.text) task else it
            }
            NoteRepository.saveTasks(context, updated)
            _tasks.value = updated
        }
    }

    fun deleteTask(context: Context, task: TaskItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = _tasks.value.filter { it != task }
            NoteRepository.saveTasks(context, updated)
            _tasks.value = updated
        }
    }
}
