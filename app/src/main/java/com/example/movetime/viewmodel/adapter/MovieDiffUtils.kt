package com.example.movetime.viewmodel.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.movetime.viewmodel.model.Movie

class MovieDiffUtils : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
}