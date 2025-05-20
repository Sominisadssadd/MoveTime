package com.example.movetime.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.movetime.R
import com.example.movetime.databinding.ActivityRegistrationBinding
import com.example.movetime.utils.SharedPreference
import com.example.movetime.view.regscreen.LoginFragment
import com.example.movetime.view.regscreen.RegistrationFragment
import com.example.movetime.view.regscreen.WelcomeFragment

class RegistrationActivity : AppCompatActivity() {

    private lateinit var authSharedPreferences: SharedPreference
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authSharedPreferences = SharedPreference(this)
        if(authSharedPreferences.isLoggedIn()){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val welcomeFragment = WelcomeFragment.newInstance {
            val loginFragment = LoginFragment.newInstance {
                val regFragment = RegistrationFragment.newInstance()
                startDestination(regFragment)
            }
            startDestination(loginFragment)
        }
        startDestination(welcomeFragment)
    }

    private fun startDestination(fragment: Fragment) {
        when (fragment) {
            is RegistrationFragment -> {
                supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(binding.regFragmentContainer.id, fragment)
                    .commit()
            }

            else -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(binding.regFragmentContainer.id, fragment)
                    .commit()
            }
        }


    }
}