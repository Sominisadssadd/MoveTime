package com.example.movetime.viewmodel.model

import java.io.Serializable

data class Movie(
    val name: String,
    val age: Int,
    val type: String,
    val duration: Int,
    val photoPath: String,
    val dateOfStart: String,
    val placeCount: Int,
    val id: Int = UNDEFINED_ID
) : Serializable {
    companion object {
        const val UNDEFINED_ID = 0
    }
}