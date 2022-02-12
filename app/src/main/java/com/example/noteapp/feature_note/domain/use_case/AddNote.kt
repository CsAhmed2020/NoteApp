package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.domain.model.InvalidNoteException
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import kotlin.jvm.Throws

/** step 15
 *
 */
class AddNote(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note:Note){
        //check if title or content is empty, we write our business logic here
        if (note.title.isBlank()){
            throw InvalidNoteException("The title of the note can't be empty")
        }
        if(note.content.isBlank()){
            throw InvalidNoteException("The content of the note can't be empty")
        }
        repository.insertNote(note)
    }
}