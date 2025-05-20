package com.example.movetime.data.db

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.movetime.data.MovieMapper
import com.example.movetime.viewmodel.MovieRepository
import com.example.movetime.viewmodel.model.Booking
import com.example.movetime.viewmodel.model.Movie
import com.example.movetime.viewmodel.model.User

class MovieRepositoryImpl(
    val application: Application
) : MovieRepository {
    private val dao = MovieDataBase.getInstance(application).getMovieDao()
    private val mapper = MovieMapper()
    override fun getMoviesList(): LiveData<List<Movie>> = MediatorLiveData<List<Movie>>().apply {
        addSource(dao.getMoviesList()) {
            value = mapper.dbMovieListToMovieList(it)
        }
    }

    override fun getMoviesByType(type: String): LiveData<List<Movie>> = MediatorLiveData<List<Movie>>().apply {
        addSource(dao.getMoviesByType(type)) {
            value = mapper.dbMovieListToMovieList(it)
        }
    }

    override suspend fun getMovie(movieId: Int): Movie {
        return mapper.dBMovieToMovie(dao.getMovie(movieId))
    }

    override fun getBookedMovies(userId: Int):LiveData<List<Booking>> = MediatorLiveData<List<Booking>>().apply {
        addSource(dao.getBookedMovies(userId)) {
            value = mapper.dbBookingListToBookingList(it)
        }
    }

    override suspend fun getUserInfo(phoneNumber: String): User {
        return mapper.dbUserToUser(dao.getUserInfo(phoneNumber))
    }

    override fun getAllBookingForCurrentFilm(idFilm: Int): LiveData<List<Int>> {
        return dao.getALlBookingForCurrentFilm(idFilm)
    }

    override suspend fun registerNewUser(user: User) {
        dao.registerNewUser(mapper.userToDbUser(user))
    }

    override suspend fun addNewMovie(movie: Movie) {
        dao.addNewMovie(mapper.movieToDbMovie(movie))
    }

    override suspend fun addNewBooking(booking: Booking) {
        dao.addNewBooking(mapper.bookingToDbBooking(booking))
    }

    override suspend fun deleteBooking(idBooking: Int): Int {
        return dao.deleteBooking(idBooking)
    }
}