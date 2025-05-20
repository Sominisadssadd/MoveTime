package com.example.movetime.viewmodel.model

data class User(
    val phone: String,
    val password: String,
    val role: String,
    val id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}