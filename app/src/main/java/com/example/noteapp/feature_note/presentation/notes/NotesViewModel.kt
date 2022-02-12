package com.example.noteapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.use_case.NotesUseCases
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/** step 14
 * firstly create States class (NoteState) & event (NotesEvent)
 */
@HiltViewModel
class NotesViewModel @Inject constructor(
private val notesUseCases: NotesUseCases
) : ViewModel(){

    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    //keep reference to last deleted note (if i need to undo deleting)
    private var recentlyDeletedNote: Note? = null

    //when we recall getNotes function in bottom we want to cancel old coroutine
    //that observe on our database.
    private var getNotesJob: Job? = null

    //initially load some notes with default order
    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent){
        when(event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    notesUseCases.deleteNote(event.note)
                    /** save deleted note as temp for undoing process */
                    recentlyDeletedNote=event.note
                }
            }

            is NotesEvent.Order -> {
                //check uf note order is actually = the note order we want to change it to
                //do nothing
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                //else
                getNotes(event.noteOrder)
            }

            //Restore deleted note
            NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    //now add (AddNote) use case (step15)
                    notesUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    //to avoid restore note multiple times set recNote is null
                    recentlyDeletedNote = null
                }

            }
            NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        //when we recall getNotes function we want to cancel old coroutine job
        //that observe on our database and create new one so we cancel old one in next line.
        getNotesJob?.cancel()
        getNotesJob = notesUseCases.getNotes(noteOrder)
            .onEach { notes -> /** notes is returned list of notes */
                _state.value=state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }
}