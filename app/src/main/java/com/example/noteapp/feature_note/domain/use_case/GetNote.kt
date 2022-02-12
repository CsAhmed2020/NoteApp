package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository

/** step 19
 * GetNote Bi Id
 */
class GetNote(
    private val repository: NoteRepository
)
{
    suspend operator fun invoke(id : Int) : Note?
    {
        return repository.getNoteById(id)
    }

}