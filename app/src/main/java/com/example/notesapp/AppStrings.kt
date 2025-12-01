package com.example.notesapp

import androidx.compose.runtime.staticCompositionLocalOf

// Current language (provided by AppNavigation)
val LocalAppLanguage = staticCompositionLocalOf { "en" }

object Strings {
    fun created(lang: String) = when (lang) {
        "fr" -> "Créé : "
        else -> "Created: "
    }

    fun addContent(lang: String) = when (lang) {
        "fr" -> "Écrivez votre note ici..."
        else -> "Write your note here..."
    }

    fun addNote(lang: String) = when (lang) {
        "fr" -> "Nouvelle note"
        else -> "New Note"
    }


    fun appName(lang: String) = when (lang) {
        "fr" -> "Meilleures Notes"
        else -> "BEST Notes App"
    }
    fun back(lang: String) = when (lang) {
        "fr" -> "Retour"
        else -> "Back"
    }

    fun pin(lang: String) = when (lang) {
        "fr" -> "Épingler"
        else -> "Pin"
    }

    fun unpin(lang: String) = when (lang) {
        "fr" -> "Désépingler"
        else -> "Unpin"
    }


    fun settings(lang: String) = when (lang) {
        "fr" -> "Paramètres"
        else -> "Settings"
    }

    fun editNote(lang: String) = when (lang) {
        "fr" -> "Modifier la note"
        else -> "Edit Note"
    }


    fun title(lang: String) = when (lang) {
        "fr" -> "Titre"
        else -> "Title"
    }

    fun search(lang: String) = when (lang) {
        "fr" -> "Rechercher..."
        else -> "Search..."
    }

    fun pinned(lang: String) = when (lang) {
        "fr" -> "Épinglé"
        else -> "Pinned"
    }

    fun save(lang: String) = when (lang) {
        "fr" -> "Enregistrer"
        else -> "Save"
    }

    fun delete(lang: String) = when (lang) {
        "fr" -> "Supprimer"
        else -> "Delete"
    }

    fun darkMode(lang: String) = when (lang) {
        "fr" -> "Mode sombre"
        else -> "Dark mode"
    }

    fun language(lang: String) = when (lang) {
        "fr" -> "Langue"
        else -> "Language"
    }
}
