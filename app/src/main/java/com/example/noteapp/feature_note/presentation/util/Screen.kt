package com.example.noteapp.feature_note.presentation.util

/** step 25
 *  define all app screens to navigate them
 */
sealed class Screen(val route : String){
    object NotesScreen : Screen("notes_screen")
    object AddEditNoteScreen : Screen("add_edit_note_screen")
}
