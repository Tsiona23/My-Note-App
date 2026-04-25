package com.example.mynoteapp.repository

import com.example.mynoteapp.database.NoteDatabase
import com.example.mynoteapp.model.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class NoteRepository(private val db: NoteDatabase) {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val notesCollection = firestore.collection("notes")

    private fun getUserId(): String? = auth.currentUser?.uid

    suspend fun insertNote(note: Note) {
        val userId = getUserId() ?: return
        val noteWithUser = note.copy(userId = userId)
        
        // 1. Save locally
        db.getNoteDao().insertNote(noteWithUser)
        
        // 2. Sync to Firebase
        try {
            notesCollection.document(noteWithUser.id).set(noteWithUser, SetOptions.merge()).await()
        } catch (e: Exception) {
            // Firestore handles offline persistence automatically if enabled
        }
    }

    suspend fun deleteNote(note: Note) {
        // 1. Delete locally
        db.getNoteDao().deleteNote(note)
        
        // 2. Delete from Firebase
        try {
            notesCollection.document(note.id).delete().await()
        } catch (e: Exception) {
            // Handle error
        }
    }

    suspend fun updateNote(note: Note) {
        val userId = getUserId() ?: return
        val noteWithUser = note.copy(userId = userId)

        // 1. Update locally
        db.getNoteDao().updateNote(noteWithUser)
        
        // 2. Update Firebase
        try {
            notesCollection.document(noteWithUser.id).set(noteWithUser, SetOptions.merge()).await()
        } catch (e: Exception) {
            // Handle error
        }
    }

    fun getAllNotes() = db.getNoteDao().getAllNotes(getUserId() ?: "")

    fun searchNote(query: String?) = db.getNoteDao().searchNote(getUserId() ?: "", query)

    suspend fun syncWithFirebase() {
        val userId = getUserId() ?: return
        try {
            val snapshot = notesCollection.whereEqualTo("userId", userId).get().await()
            val remoteNotes = snapshot.toObjects(Note::class.java)
            for (note in remoteNotes) {
                db.getNoteDao().insertNote(note)
            }
        } catch (e: Exception) {
            // Handle error
        }
    }
}
