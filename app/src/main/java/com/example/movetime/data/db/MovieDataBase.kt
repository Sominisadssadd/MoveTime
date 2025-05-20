package com.example.movetime.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movetime.data.dbmodel.DbBooking
import com.example.movetime.data.dbmodel.DbMovie
import com.example.movetime.data.dbmodel.DbUser

@Database(
    entities = [DbBooking::class, DbMovie::class, DbUser::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDataBase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    companion object {
        private const val DB_NAME = "cinema.db"
        private var instance: MovieDataBase? = null
        private val MONITOR = Any()

        fun getInstance(application: Application): MovieDataBase {
            synchronized(MONITOR) {
                instance?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    MovieDataBase::class.java,
                    DB_NAME
                ).build()
                instance = db
                return db
            }
        }
    }

}