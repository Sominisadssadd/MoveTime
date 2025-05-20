package com.example.movetime.utils

import android.content.Context

class SharedPreference(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(AUTH_STATE, Context.MODE_PRIVATE)

    fun setLoginStatus(isLogged: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGIN, isLogged).apply()
    }

    fun setIdData(id: Int) {
        sharedPreferences.edit().putInt(KEY_ID_DATA, id).apply()
    }

    fun getUserID() = sharedPreferences.getInt(KEY_ID_DATA, 0)
    fun isLoggedIn() = sharedPreferences.getBoolean(KEY_IS_LOGIN, false)

    companion object {
        private const val AUTH_STATE = "auth_state"
        const val KEY_IS_LOGIN = "isLogin"
        const val KEY_ID_DATA = "id"
    }
}