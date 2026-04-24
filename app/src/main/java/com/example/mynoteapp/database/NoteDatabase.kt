package com.example.mynoteapp.database

import android.content.Context
import androidx.annotation.UiContext
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.mynoteapp.model.Note
import kotlinx.coroutines.internal.SynchronizedObject
import java.util.concurrent.locks.Lock

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun getNoteDao(): NoteDao

    companion object{
        @Volatile
        private var instance: NoteDatabase? = null
        private val Lock = Any()
        operator fun invoke(context: Context) = instance ?:
        synchronized(Lock){
            instance ?:
            createDtabase(context).also{
                instance = it
            }
        }
        private fun createDtabase(context: Context) =
            databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_db"
            ).build()
    }
}