package com.example.movetime.viewmodel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movetime.databinding.BookingItemBinding
import com.example.movetime.databinding.MovieItemBinding
import com.example.movetime.viewmodel.model.Movie
import com.squareup.picasso.Picasso

class BookingAdapter() : ListAdapter<Movie, BookingAdapter.MovieViewHolder>(MovieDiffUtils()) {

    var onItemClickListener: ((Movie) -> Unit)? = null

    inner class MovieViewHolder(val binding: BookingItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BookingItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            imageViewMovie.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
            Picasso.get().load(item.photoPath).into(imageViewMovie)
            tvMovieFilm.text = item.name
            tvMovieType.text = "Жанр: ${item.type}"
            tvDuration.text = "Длительность: ${item.duration}мин"
            dateOfStart.text = "Дата показа: ${item.dateOfStart}"
            tvAge.text = "${item.age}+"
            buttonRemoveBooking.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
        }

    }
}