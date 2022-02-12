package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** step 6
 *the use case contain our business logic
 * every use case have a single public function to be called from outside
 * they make our code readable because it is a single thing our app does / single user action
 * it can be reuse in different viewModels
 * ex : (get note / add note / edit note) use case
 * ViewModel will call use cases
 */
class GetNotes (
    private val repository: NoteRepository
    )
{

    /**
     * we will put our business logic here
     * we use operator to use invoke
     * invoke,lets an instance of a class have a default function implementation with no need to call it.
     * invoke will implement automatically when create class instance
     * firstly det orderType & NoteOrder (in utility class)
     */
     operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ) : Flow<List<Note>> {
        //map returned data in new list to order them
         return repository.getNotes().map { notes ->
             when(noteOrder.orderType){
                 is OrderType.Ascending -> {
                     when(noteOrder){
                         is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                         is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                         is NoteOrder.Color -> notes.sortedBy { it.color }
                     }
                 }
                 is OrderType.Descending -> {
                     when(noteOrder){
                         is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                         is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                         is NoteOrder.Color -> notes.sortedByDescending { it.color }
                     }
                 }
             }
         }
    }
}