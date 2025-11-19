package com.example.notesapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewNoteScreen(noteId: Long, navController: NavController) {

    val context = LocalContext.current
    val notes = NoteRepository.loadNotes(context)
    val note = notes.find { it.id == noteId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(note?.title ?: "") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (note != null) {
                            deleteNote(note.id, context, navController)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Note"
                        )
                    }
                }
            )


        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = note?.content ?: "",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
fun deleteNote(noteId: Long, context: android.content.Context, navController: NavController) {
    val notes = NoteRepository.loadNotes(context)
    val updatedNotes = notes.filter { it.id != noteId }

    NoteRepository.saveNotes(context, updatedNotes)

    navController.popBackStack()
}
