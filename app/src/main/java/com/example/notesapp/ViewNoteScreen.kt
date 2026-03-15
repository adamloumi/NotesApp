package com.example.notesapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ViewNoteScreen(
    noteId: Long,
    navController: NavController,
    noteViewModel: NoteViewModel = viewModel()
) {
    val context = LocalContext.current
    val notes by noteViewModel.notes.collectAsState()
    val note = notes.find { it.id == noteId }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(context) {
        noteViewModel.loadNotes(context)
    }

    if (note == null) {
        LaunchedEffect(Unit) { navController.popBackStack() }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(note!!.title) },
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
                        navController.navigate("edit_note/${note!!.id}")
                    }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit Note")
                    }

                    IconButton(onClick = {
                        coroutineScope.launch {
                            noteViewModel.pinNote(context, note!!.id, !note!!.pinned)
                        }
                    }) {
                        Icon(
                            imageVector = if (note!!.pinned) Icons.Filled.Star else Icons.Filled.StarBorder,
                            contentDescription = "Pin"
                        )
                    }

                    IconButton(onClick = {
                        coroutineScope.launch {
                            noteViewModel.deleteNote(context, note!!.id)
                            navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {

            // TIMESTAMP
            Text(
                text = "Created: ${note!!.timestamp}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))


            // FULL NOTE CONTENT
            SelectionContainer {
                Text(
                    text = note!!.content,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}


// deleteNote function is now handled by NoteViewModel and coroutine in the composable
