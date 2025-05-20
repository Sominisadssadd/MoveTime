package com.example.movetime.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movetime.data.dbmodel.DbBooking
import com.example.movetime.data.dbmodel.DbMovie
import com.example.movetime.data.dbmodel.DbUser

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getMoviesList(): LiveData<List<DbMovie>>

    @Query("SELECT * FROM movie WHERE type =:type")
    fun getMoviesByType(type: String): LiveData<List<DbMovie>>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovie(movieId: Int): DbMovie

    @Query("SELECT * FROM booking WHERE user_id = :userId")
    fun getBookedMovies(userId: Int): LiveData<List<DbBooking>>

    @Query("SELECT * FROM users WHERE phone = :phoneNumber")
    fun getUserInfo(phoneNumber: String): DbUser

    @Query("SELECT place_number FROM booking where film_id = :idFilm") //
    fun getALlBookingForCurrentFilm(idFilm: Int): LiveData<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) //
    fun registerNewUser(user: DbUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //
    fun addNewMovie(movie: DbMovie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)  //
    fun addNewBooking(booking: DbBooking)

    @Query("delete from booking where film_id =:idBooking")  //удаляет все брони по id фильму
    fun deleteBooking(idBooking: Int): Int
}