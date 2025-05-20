package com.example.movetime.data.dbmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movetime.data.dbmodel.DbBooking.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class DbBooking(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(ID_TABLE)
    val id: Int,
    @ColumnInfo(FILM_ID_TABLE)
    val filmId: Int,
    @ColumnInfo(PLACE_NUMBER_TABLE)
    val placeNumber: Int,
    @ColumnInfo(USER_ID_TABLE)
    val userId: Int
) {
    companion object {
        const val TABLE_NAME = "booking"
        const val ID_TABLE = "id"
        const val FILM_ID_TABLE = "film_id"
        const val PLACE_NUMBER_TABLE = "place_number"
        const val USER_ID_TABLE = "user_id"
    }
}