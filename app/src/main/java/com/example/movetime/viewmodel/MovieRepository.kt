package com.example.movetime.viewmodel

import androidx.lifecycle.LiveData
import com.example.movetime.viewmodel.model.Booking
import com.example.movetime.viewmodel.model.Movie
import com.example.movetime.viewmodel.model.User

interface MovieRepository {
    fun getMoviesList(): LiveData<List<Movie>>
    fun getMoviesByType(type: String): LiveData<List<Movie>>
    suspend fun getMovie(movieId: Int): Movie
    fun getBookedMovies(userId: Int): LiveData<List<Booking>>
    suspend fun getUserInfo(phoneNumber: String): User
    fun getAllBookingForCurrentFilm(idFilm: Int): LiveData<List<Int>>
    suspend fun registerNewUser(user: User)
    suspend fun addNewMovie(movie: Movie)
    suspend fun addNewBooking(booking: Booking)
    suspend fun deleteBooking(idBooking: Int): Int
}