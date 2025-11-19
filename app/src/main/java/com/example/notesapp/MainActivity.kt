package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesApp(navController: NavController) {

    var showAddDialog by remember { mutableStateOf(false) }

    // Temporary sample notes
    val context = LocalContext.current

    var notes by remember { mutableStateOf(listOf<Note>()) }

    LaunchedEffect(Unit) {
        notes = NoteRepository.loadNotes(context)
    }




    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BEST Notes App by LEI YUN") }
            )



        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showAddDialog = true
            }) {
                Text("+")
            }
        }

    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {

            // Show each note title
            for (note in notes) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .clickable {
                            navController.navigate("view_note/${note.id}")
                        }
                )


            }
        }
    }
    if (showAddDialog) {
        AddNoteDialog(
            onDismiss = { showAddDialog = false },
            onSave = { title, content ->
                val newNote = Note(
                    id = System.currentTimeMillis(),
                    title = title,
                    content = content
                )

                notes = notes + newNote
                NoteRepository.saveNotes(context, notes)
                showAddDialog = false

            }

        )
    }
}
@Composable
fun AddNoteDialog(
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Note") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(title, content)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
