package com.example.mynoteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.mynoteapp.model.Note
import com.example.mynoteapp.repository.NoteRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class NotesViewModel(app: Application, private val noteRepository: NoteRepository): AndroidViewModel(app) {

    private val currentUserId = MutableLiveData<String?>()

    init {
        // Observe auth state changes
        FirebaseAuth.getInstance().addAuthStateListener { auth ->
            val userId = auth.currentUser?.uid
            currentUserId.postValue(userId)
            if (userId != null) {
                noteRepository.startRealTimeSync(userId)
            } else {
                noteRepository.stopRealTimeSync()
            }
        }
    }

    // This will automatically switch the notes list whenever the user logs in/out
    val notes: LiveData<List<Note>> = currentUserId.switchMap { userId ->
        if (userId != null) {
            noteRepository.getAllNotes(userId)
        } else {
            MutableLiveData(emptyList())
        }
    }

    fun addNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }

    fun searchNote(query: String?) = currentUserId.switchMap { userId ->
        noteRepository.searchNote(userId ?: "", query)
    }

    override fun onCleared() {
        super.onCleared()
        noteRepository.stopRealTimeSync()
    }
}
