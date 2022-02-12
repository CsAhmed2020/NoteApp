package com.example.noteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp.ui.theme.*
import java.lang.Exception

/** Step 1 after struct the app as clean architecture & add dependencies
 * create base model / Dao
  */
@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
)
{
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

//this class will be using as exception when we insert invalid note
// (like : note that have no title) in step 15
class InvalidNoteException(message: String): Exception(message)