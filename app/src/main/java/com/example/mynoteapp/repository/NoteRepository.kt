package com.example.mynoteapp.repository

import android.util.Log
import com.example.mynoteapp.database.NoteDatabase
import com.example.mynoteapp.model.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class NoteRepository(private val db: NoteDatabase) {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val notesCollection = firestore.collection("notes")
    private var syncListener: ListenerRegistration? = null

    private fun getUserId(): String? = auth.currentUser?.uid

    suspend fun insertNote(note: Note) {
        val userId = getUserId() ?: return
        val noteWithUser = note.copy(userId = userId)
        
        db.getNoteDao().insertNote(noteWithUser)
        
        try {
            notesCollection.document(noteWithUser.id).set(noteWithUser, SetOptions.merge()).await()
        } catch (e: Exception) {
            Log.e("NoteRepository", "Error syncing note: ${e.message}")
        }
    }

    suspend fun deleteNote(note: Note) {
        db.getNoteDao().deleteNote(note)
        try {
            notesCollection.document(note.id).delete().await()
        } catch (e: Exception) {
            Log.e("NoteRepository", "Error deleting note: ${e.message}")
        }
    }

    suspend fun updateNote(note: Note) {
        val userId = getUserId() ?: return
        val noteWithUser = note.copy(userId = userId)

        db.getNoteDao().updateNote(noteWithUser)
        
        try {
            notesCollection.document(noteWithUser.id).set(noteWithUser, SetOptions.merge()).await()
        } catch (e: Exception) {
            Log.e("NoteRepository", "Error updating note: ${e.message}")
        }
    }

    fun getAllNotes(userId: String) = db.getNoteDao().getAllNotes(userId)

    fun searchNote(userId: String, query: String?) = db.getNoteDao().searchNote(userId, query)

    // ✅ Start real-time sync for a specific user
    fun startRealTimeSync(userId: String) {
        // Remove existing listener if any to avoid duplicates or leaks
        syncListener?.remove()

        syncListener = notesCollection.whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("NoteRepository", "Firestore Listen failed.", error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val remoteNotes = snapshot.toObjects(Note::class.java)
                    CoroutineScope(Dispatchers.IO).launch {
                        for (note in remoteNotes) {
                            db.getNoteDao().insertNote(note)
                        }
                    }
                }
            }
    }

    fun stopRealTimeSync() {
        syncListener?.remove()
        syncListener = null
    }
}
