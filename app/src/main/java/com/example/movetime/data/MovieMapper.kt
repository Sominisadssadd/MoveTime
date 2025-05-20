package com.example.movetime.data

import com.example.movetime.data.dbmodel.DbBooking
import com.example.movetime.data.dbmodel.DbMovie
import com.example.movetime.data.dbmodel.DbUser
import com.example.movetime.viewmodel.model.Booking
import com.example.movetime.viewmodel.model.Movie
import com.example.movetime.viewmodel.model.User

class MovieMapper {

    fun dbUserToUser(dbUser: DbUser): User {
        with(dbUser) {
            return User(
                phone, password, role, id
            )
        }
    }

    fun userToDbUser(user: User): DbUser {
        with(user) {
            return DbUser(
                id, phone, password, role
            )
        }
    }

    fun dBMovieToMovie(dbMovie: DbMovie): Movie {
        with(dbMovie) {
            return Movie(
                name, age, type, duration, photoPath, dateOfStart, placeCount, id
            )
        }
    }

    fun movieToDbMovie(movie: Movie): DbMovie {
        with(movie) {
            return DbMovie(
                id, name, age, type, duration, photoPath, dateOfStart, placeCount
            )
        }
    }

    fun dbBookingToBooking(dbBooking: DbBooking) = with(dbBooking) {
        Booking(
            filmId, placeNumber, userId, id
        )
    }

    fun bookingToDbBooking(booking: Booking) = with(booking) {
        DbBooking(
            id, filmId, placeNumber, userId
        )
    }

    fun dbMovieListToMovieList(movieList: List<DbMovie>) = movieList.map {
        dBMovieToMovie(it)
    }

    fun dbBookingListToBookingList(booking: List<DbBooking>) = booking.map {
        dbBookingToBooking(it)
    }


}