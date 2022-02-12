package com.example.noteapp.feature_note.domain.repository

import com.example.noteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

/** Step 4
 * the reason that's interface is that is just good for testing as then
 * we can easily create fake version of this repository to pass to out use cases in that case.
 * Using repo , user case doesn't care where data come from api or local db.
 * there we will define all Dao queries functions
 */
interface NoteRepository {

    fun getNotes():Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}