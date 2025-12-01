package com.example.notesapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    var selectedTab by remember { mutableStateOf(0) }

    val tabs = listOf("Notes", "To-Do")

    Column {

        // TOP BAR (same as before)
        val lang = LocalAppLanguage.current

        NotesTopBar(
            navController = navController,
            isSearching = false,
            onSearchClick = {},
            searchQuery = "",
            onSearchChange = {},
            onCloseSearch = {},
            lang = lang
        )


        // TAB BAR
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        // SCREEN AREA
        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTab) {
                0 -> NotesApp(navController)   // NOTES SCREEN
                1 -> TasksScreen(navController) // TASKS SCREEN
            }
        }
    }
}
