package com.example.movetime.view.adminscreen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.movetime.R
import com.example.movetime.databinding.ActivityAdminScreenBinding
import com.example.movetime.viewmodel.AdminViewModel
import com.example.movetime.viewmodel.AdminViewModelFactory
import com.example.movetime.viewmodel.model.Movie
import com.google.android.material.snackbar.Snackbar

class AdminScreen : AppCompatActivity() {

    private lateinit var binding: ActivityAdminScreenBinding
    private lateinit var viewModel: AdminViewModel
    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()

    }

    private fun setUpViews() {
        val adminViewModelFactory = AdminViewModelFactory(application)
        viewModel = ViewModelProvider(this, adminViewModelFactory)[AdminViewModel::class.java]
        viewModel.error.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
        binding.saveMovieButton.setOnClickListener {
            saveMovie()
        }
    }

    private fun saveMovie() {
        val movieName = binding.editMovieName.text.toString()
        val movieAge = binding.editMovieAge.text.toString().toInt()
        val movieType = binding.editMovieType.text.toString()
        val movieDuration = binding.editMovieDuration.text.toString().toInt()
        val movieDate = binding.editMovieDateOfStart.text.toString()
        val placeCount = binding.editPlaceCount.text.toString().toInt()
        val imageURL = binding.edImageURL.text.toString()
        val movie = Movie(
            movieName,
            movieAge,
            movieType,
            movieDuration,
            imageURL,
            movieDate,
            placeCount
        )
        viewModel.addNewMovie(movie)
        binding.apply {
            editMovieName.text = null
            editMovieAge.text = null
            editMovieType.text= null
            editMovieDuration.text= null
            editMovieDateOfStart.text= null
            editPlaceCount.text= null
            edImageURL.text= null

        }


    }

    private fun choicePhoto() {
        openGallery()
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
        }
    }
}