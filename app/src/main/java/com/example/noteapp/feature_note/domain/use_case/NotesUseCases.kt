package com.example.noteapp.feature_note.domain.use_case

/** step 10
 * here it contain all app use cases to avoid big code in view model constructor
 */
data class NotesUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote : GetNote
)