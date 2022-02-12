package com.example.noteapp.feature_note.presentation.add_edit_note

/** step 20
 * Custom TextField class to save TextField state & control hint state
 */
data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
