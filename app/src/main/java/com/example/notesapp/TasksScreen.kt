package com.example.notesapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import com.example.notesapp.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun TasksScreen(
    navController: NavController,
    tasksViewModel: TasksViewModel = viewModel()
) {
    val context = LocalContext.current
    val tasks by tasksViewModel.tasks.collectAsState()
    var newTask by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(context) {
        tasksViewModel.loadTasks(context)
    }

    Scaffold(

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            // Add new task
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = newTask,
                    onValueChange = { newTask = it },
                    placeholder = { Text("New task...") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                Button(onClick = {
                    if (newTask.isNotBlank()) {
                        coroutineScope.launch {
                            tasksViewModel.addTask(context, TaskItem(newTask, false))
                            newTask = ""
                        }
                    }
                }) {
                    Text("Add")
                }
            }

            Spacer(Modifier.height(16.dp))

            // List of tasks
            LazyColumn {
                items(tasks) { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Toggle done/undone
                        IconButton(onClick = {
                            coroutineScope.launch {
                                tasksViewModel.updateTask(context, task.copy(done = !task.done))
                            }
                        }) {
                            Icon(
                                imageVector = if (task.done)
                                    Icons.Filled.CheckCircle
                                else
                                    Icons.Filled.RadioButtonUnchecked,
                                contentDescription = "Toggle Task"
                            )
                        }

                        Text(
                            text = task.text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )

                        // Delete task
                        IconButton(onClick = {
                            coroutineScope.launch {
                                tasksViewModel.deleteTask(context, task)
                            }
                        }) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Delete task"
                            )
                        }
                    }
                }
            }
        }
    }
}
