package com.example.movetime.viewmodel.model

data class Booking(
    val filmId: Int,
    val placeNumber: Int,
    val userId: Int,
    val id: Int = UNDEFINED_ID

) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}