package com.example.mynoteapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mynoteapp.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM NOTES WHERE userId = :userId ORDER BY timestamp DESC")
    fun getAllNotes(userId: String): LiveData<List<Note>>

    @Query("SELECT * FROM NOTES WHERE userId = :userId AND (noteTitle LIKE :query OR noteDesc LIKE :query)")
    fun searchNote(userId: String, query: String?): LiveData<List<Note>>
}
