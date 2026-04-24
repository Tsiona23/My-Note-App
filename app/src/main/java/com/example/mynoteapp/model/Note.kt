package com.example.mynoteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val noteTitle: String,
    val noteDesc: String,
) : Parcelable
