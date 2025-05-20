package com.example.movetime.view

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.movetime.R
import com.example.movetime.databinding.ActivityMovieDescriptionBinding
import com.example.movetime.utils.SharedPreference
import com.example.movetime.viewmodel.DescriptionFilmViewModel
import com.example.movetime.viewmodel.DescriptionViewModelFactory
import com.example.movetime.viewmodel.model.Booking
import com.example.movetime.viewmodel.model.Movie
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType

class MovieDescriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDescriptionBinding
    private var movie: Movie? = null
    private lateinit var authSharedPreferences: SharedPreference

    private lateinit var viewModel: DescriptionFilmViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authSharedPreferences = SharedPreference(this)
        val factory = DescriptionViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)[DescriptionFilmViewModel::class.java]
        movie = intent.getSerializableExtra("EXTRA_USER") as Movie


        binding.apply {
            Picasso.get().load(movie?.photoPath).into(imageViewMovie)
            tvMovieFilm.text = movie?.name
            tvMovieType.text = "Жанр: ${movie?.type}"
            tvDuration.text = "Длительность: ${movie?.duration}"
            tvAge.text = "${movie?.age}+"
        }

        observe()
    }

    private fun observe() {
        viewModel.bookingInfo(movie!!.id).observe(this) {
            binding.gridLayout.removeAllViews()
            for (i in 1..movie!!.placeCount) {
                val button = Button(this).apply {
                    text = i.toString() // Установка номера места
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 0
                        setMargins(5, 5, 5, 5)
                        if (i in it) setBackgroundColor(getColor(R.color.red)) else setBackgroundColor(
                            getColor(R.color.green)
                        )
                        height = GridLayout.LayoutParams.WRAP_CONTENT
                        columnSpec = GridLayout.spec(
                            (i - 1) % 5,
                            1f
                        ) // Устанавливаем вес для равномерного распределения
                        rowSpec = GridLayout.spec((i - 1) / 5)
                    }
                    setOnClickListener {
                        val booking = Booking(movie!!.id, i, authSharedPreferences.getUserID())
                        viewModel.bookingPlace(booking)
                    }

                }
                binding.gridLayout.addView(button)

            }
        }
        viewModel.error.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.success.observe(this){
            AestheticDialog.Builder(
                this@MovieDescriptionActivity,
                DialogStyle.TOASTER,
                DialogType.SUCCESS
            )
                .setGravity(Gravity.CENTER)
                .setTitle("Уведомление")
                .setMessage("Успешно забронированно")
                .show()
        }
    }

}