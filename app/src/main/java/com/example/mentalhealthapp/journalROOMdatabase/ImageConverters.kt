package com.example.mentalhealthapp.journalROOMdatabase

import androidx.room.TypeConverter

class ImageConverters {
    @TypeConverter
    fun formStringList(value: List<String>): String = value.joinToString(",")

    @TypeConverter
    fun toStringList(value: String): List<String> = value.split(",")

}