package com.example.mynoteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "",
    val noteTitle: String,
    val noteDesc: String,
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable {
    // Required for Firebase
    constructor() : this("", "", "", "", 0)
}
