package com.example.noteapp.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.feature_note.domain.model.InvalidNoteException
import com.example.noteapp.feature_note.domain.model.Note
import com.example.noteapp.feature_note.domain.use_case.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/** step 21
 *
 */
@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases,
    /** we pass savedStateHandle to get opened note id */
    savedStateHandle: SavedStateHandle
) : ViewModel()
{
    //Firstly create NoteTextFieldState class to save TextFields states (step 20)

    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = "Enter title..."
    ))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(
        hint = "Enter some content"
    ))
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private var currentNoteId : Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) /** -1 = when click on add note fab with no data added */
            {
               viewModelScope.launch {
                  notesUseCases.getNote(noteId)?.also { note ->
                      currentNoteId = note.id
                      //update values
                      _noteTitle.value=noteTitle.value.copy(
                          text = note.title,
                          isHintVisible = false
                      )
                      _noteContent.value = _noteContent.value.copy(
                          text = note.content,
                          isHintVisible = false
                      )
                      _noteColor.value = note.color
                   }
               }
            }
        }
    }
    /**
     * in jetpack compose if we use normal compose states (like above) it hold and save the state,
     * for ex in color state it save color even if screen rotated , it doesn't changed.
     * if we don't need save state more like when we rotate the screen , we use MutableSharedFlow
     * in next line we will send one-time event from our view model in MutableSharedFlow,
     * and then observe in ui (show snackbar when click on save)
     */

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    //Firstly Create AddEditNoteEvent class (step 22)
    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteContent.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    /** if i not focus on textField or empty show the hint */
                  isHintVisible = !event.focusState.isFocused &&
                          noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = _noteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        notesUseCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e:InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }

    }

    sealed class UiEvent {
        data class ShowSnackbar(val message:String) : UiEvent()
        object SaveNote: UiEvent()
    }
}