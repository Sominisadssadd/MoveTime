package com.example.movetime.data.dbmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movetime.data.dbmodel.DbUser.Companion.USERS_TABLE


@Entity(tableName = USERS_TABLE)
data class DbUser(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(ID_TABLE)
    val id: Int,
    @ColumnInfo(PHONE_TABLE)
    val phone: String,
    @ColumnInfo(PASSWORD_TABLE)
    val password: String,
    @ColumnInfo(ROLE_TABLE)
    val role: String
) {
    companion object {
        const val USERS_TABLE = "users"
        const val ID_TABLE = "id"
        const val PHONE_TABLE = "phone"
        const val PASSWORD_TABLE = "password"
        const val ROLE_TABLE = "role"
    }
}