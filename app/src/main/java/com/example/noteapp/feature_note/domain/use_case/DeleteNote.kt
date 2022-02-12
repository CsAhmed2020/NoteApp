package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository

/** step 9
 * delete note use case
 */
class DeleteNote (
    private val repository: NoteRepository
        )
{
    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}