package com.example.noteapp.feature_note.presentation.notes

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.util.NoteOrder

/** step 13
 *  here we define events that i can do on app screens
 */
sealed class NotesEvent {

    data class Order(val noteOrder: NoteOrder):NotesEvent()
    data class DeleteNote(val note: Note):NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
