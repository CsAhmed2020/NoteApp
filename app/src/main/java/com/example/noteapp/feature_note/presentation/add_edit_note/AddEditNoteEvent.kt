package com.example.noteapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

/** step 22
 *  it's like Event in notes screen (step 13)
 *  all events i can do in this screen
 */
sealed class AddEditNoteEvent {
    data class EnteredTitle(val value: String) : AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditNoteEvent()
    data class EnteredContent(val value: String) : AddEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState) : AddEditNoteEvent()
    data class ChangeColor(val color: Int) : AddEditNoteEvent()
    object SaveNote : AddEditNoteEvent()
}
