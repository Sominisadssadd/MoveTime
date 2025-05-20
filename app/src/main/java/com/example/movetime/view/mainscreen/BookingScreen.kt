package com.example.movetime.view.mainscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movetime.R
import com.example.movetime.databinding.FragmentBookingScreenBinding
import com.example.movetime.databinding.FragmentLoginBinding
import com.example.movetime.utils.SharedPreference
import com.example.movetime.viewmodel.BookingViewModel
import com.example.movetime.viewmodel.BookingViewModelFactory
import com.example.movetime.viewmodel.UserViewModel
import com.example.movetime.viewmodel.adapter.BookingAdapter

class BookingScreen : Fragment() {


    private var _binding: FragmentBookingScreenBinding? = null
    private val binding: FragmentBookingScreenBinding
        get() = _binding ?: throw RuntimeException(
            "FragmentBookingScreenBinding Fragment null"
        )
    private lateinit var viewModel: BookingViewModel
    private lateinit var adapter: BookingAdapter
    private lateinit var authSharedPreferences: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookingScreenBinding.inflate(inflater, container, false)
        val factory = BookingViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[BookingViewModel::class.java]
        authSharedPreferences = SharedPreference(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observe()
    }

    private fun observe() {
        viewModel.bookingList(authSharedPreferences.getUserID()).observe(viewLifecycleOwner) {
            val listOfBookedIdMovies = mutableSetOf<Int>()
            it.forEach {
                listOfBookedIdMovies.add(it.filmId)
            }
            viewModel.getMovieListByEachMovieId(listOfBookedIdMovies.toList())
        }
        viewModel.bookedMovies.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.apply {
                    lottieNoTicket.visibility = View.VISIBLE
                    tvNothing.visibility = View.VISIBLE
                }
            }
            adapter.submitList(it)
        }
    }

    private fun setUpRecyclerView() {
        binding.apply {
            adapter = BookingAdapter()
            bookingList.layoutManager = LinearLayoutManager(requireContext())
            bookingList.adapter = adapter
            adapter.onItemClickListener = {
                viewModel.removeBooking(it.id)
            }
        }
    }

    companion object {

        fun newInstance() = BookingScreen()
    }
}