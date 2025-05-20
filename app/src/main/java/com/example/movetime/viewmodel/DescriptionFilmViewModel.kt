package com.example.movetime.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movetime.data.db.MovieRepositoryImpl
import com.example.movetime.viewmodel.model.Booking
import com.example.movetime.viewmodel.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DescriptionFilmViewModel(application: Application) : AndroidViewModel(application) {

    private val repositoryImpl = MovieRepositoryImpl(application)

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun bookingInfo(idFilm: Int) = repositoryImpl.getAllBookingForCurrentFilm(idFilm)

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

    fun bookingPlace(booking: Booking) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repositoryImpl.addNewBooking(booking)
                _success.postValue(Unit)
            } catch (e: Exception) {
                _error.postValue("Не удалось забронировать")
            }
        }
    }


}