package com.example.notesapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    var isDarkTheme by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        ThemeStore.getTheme(context).collect { savedTheme ->
            isDarkTheme = savedTheme
        }
    }

    MaterialTheme(
        colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
    ) {
        NavHost(
            navController = navController,
            startDestination = "notes_list",
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            enterTransition = {
                slideInHorizontally(animationSpec = tween(300)) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(animationSpec = tween(300)) + fadeOut()
            }
        ) {



            composable("notes_list") {
                NotesApp(navController)
            }

            composable(
                route = "edit_note/{noteId}",
                arguments = listOf(navArgument("noteId") { type = NavType.LongType })
            ) {
                val noteId = it.arguments?.getLong("noteId") ?: 0L
                EditNoteScreen(noteId, navController)
            }

            composable(
                route = "view_note/{noteId}",
                arguments = listOf(navArgument("noteId") { type = NavType.LongType })
            ) {
                val noteId = it.arguments?.getLong("noteId") ?: 0L
                ViewNoteScreen(noteId, navController)
            }

            composable("settings") {
                SettingsScreen(
                    navController = navController,
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = {
                        val newValue = !isDarkTheme
                        isDarkTheme = newValue
                        scope.launch { ThemeStore.saveTheme(context, newValue) }
                    }
                )
            }

            composable("add_note") {
                AddNoteScreen(navController)
            }
        }

    }}