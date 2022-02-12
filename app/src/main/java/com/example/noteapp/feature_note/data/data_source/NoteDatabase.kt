package com.example.noteapp.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp.feature_note.domain.model.Note

/** Step 3
 * Create DB class
 */
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao : NoteDao

    // we will use it later with hilt DB provider (step 12)
    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}