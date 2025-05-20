package com.example.movetime.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movetime.ERROR_MESSAGE
import com.example.movetime.data.db.MovieRepositoryImpl
import com.example.movetime.viewmodel.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repositoryImpl = MovieRepositoryImpl(application)

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User>
        get() = _userInfo

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

    fun registerNewUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repositoryImpl.registerNewUser(user)
                setSuccessTrueIOThread()
            } catch (e: Exception) {
                _error.postValue(ERROR_MESSAGE)

            }
        }
    }

    fun getUserInfo(phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _userInfo.postValue(repositoryImpl.getUserInfo(phoneNumber))
            } catch (e: Exception) {
                setError("Такого пользователя нет")

            }
        }
    }
}
