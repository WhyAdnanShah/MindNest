package com.example.mentalhealthapp.journalROOMdatabase

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Immutable
@Entity(tableName = "Journal_table")
data class JournalEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var title: String = "",
    var content: String = "",
    var date: String = "",
//    var images: Uri
)