package com.example.movetime.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.movetime.R
import com.example.movetime.databinding.ActivityMainBinding
import com.example.movetime.utils.File
import com.example.movetime.view.mainscreen.BookingScreen
import com.example.movetime.view.mainscreen.MovieScreen

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
        val fragmentMovie = MovieScreen.newInstance()
        startDestination(fragmentMovie)
        val filePermission = File(this)
        filePermission.checkUserPermissions()
    }

    fun setupBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.movie_nav -> {
                    val fragmentMovie = MovieScreen.newInstance()
                    startDestination(fragmentMovie)
                    true
                }

                R.id.booking_nav -> {
                    val fragmentBooking = BookingScreen.newInstance()
                    startDestination(fragmentBooking)
                    true
                }

                else -> {
                    throw RuntimeException("Unknown fragment")
                }
            }
        }
    }

    fun startDestination(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainContainerFragment.id, fragment)
            .commit()
    }
}