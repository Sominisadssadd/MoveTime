package com.example.movetime.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movetime.data.db.MovieRepositoryImpl
import com.example.movetime.viewmodel.model.Booking
import com.example.movetime.viewmodel.model.Movie
import com.example.movetime.viewmodel.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookingViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repositoryImpl = MovieRepositoryImpl(application)

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error


    fun bookingList(userId: Int): LiveData<List<Booking>> = repositoryImpl.getBookedMovies(userId)


    private val _bookedMovies = MutableLiveData<List<Movie>>()
    val bookedMovies: LiveData<List<Movie>>
        get() = _bookedMovies

    fun getMovieListByEachMovieId(listOfNumbers: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val listOfMovies = mutableListOf<Movie>()
                listOfNumbers.forEach {
                    val movie = repositoryImpl.getMovie(it)
                    listOfMovies.add(movie)
                }
                _bookedMovies.postValue(listOfMovies)
            } catch (e: Exception) {
                _error.postValue("Невозможно получить список фильмов")
            }
        }
    }

    private val _success = MutableLiveData<Unit>()
    val success: LiveData<Unit>
        get() = _success

    fun setSuccessTrueMainThread() {
        _success.value = Unit
    }

    fun setSuccessTrueIOThread() {
        _success.postValue(Unit)
    }

    fun setError(message: String) {
        _error.postValue(message)
    }


    fun removeBooking(idFilm: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repositoryImpl.deleteBooking(idFilm)
            } catch (e: Exception) {
                _error.postValue("Отменить бронь не удалось")
            }
        }
    }

}