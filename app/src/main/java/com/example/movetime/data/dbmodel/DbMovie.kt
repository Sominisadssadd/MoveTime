package com.example.movetime.data.dbmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movetime.data.dbmodel.DbMovie.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class DbMovie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(ID_TABLE)
    val id: Int,
    @ColumnInfo(NAME_TABLE)
    val name: String,
    @ColumnInfo(AGE_TABLE)
    val age: Int,
    @ColumnInfo(TYPE_TABLE)
    val type: String,
    @ColumnInfo(DURATION_TABLE)
    val duration: Int,
    @ColumnInfo(PHOTO_PATH_TABLE)
    val photoPath: String,
    @ColumnInfo(DATE_OF_START_TABLE)
    val dateOfStart: String,
    @ColumnInfo(PLACE_COUNT_TABLE)
    val placeCount: Int
) {
    companion object {
        const val TABLE_NAME = "movie"
        const val ID_TABLE = "id"
        const val NAME_TABLE = "name"
        const val AGE_TABLE = "age"
        const val TYPE_TABLE = "type"
        const val DURATION_TABLE = "duration"
        const val PHOTO_PATH_TABLE = "photo_path"
        const val DATE_OF_START_TABLE = "date"
        const val PLACE_COUNT_TABLE = "place_count"
    }
}