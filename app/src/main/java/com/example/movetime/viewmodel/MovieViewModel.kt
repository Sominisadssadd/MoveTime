package com.example.movetime.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movetime.data.db.MovieRepositoryImpl
import com.example.movetime.viewmodel.model.Movie
import com.example.movetime.viewmodel.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val repositoryImpl = MovieRepositoryImpl(application)

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error


    fun getMovieByType(type: String): LiveData<List<Movie>> {
        return repositoryImpl.getMoviesByType(type)
    }

    val movieList: LiveData<List<Movie>> = repositoryImpl.getMoviesList()

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
}