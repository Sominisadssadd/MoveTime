package com.example.movetime.view.mainscreen

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movetime.R
import com.example.movetime.databinding.FragmentLoginBinding
import com.example.movetime.databinding.FragmentMovieCreenBinding
import com.example.movetime.view.MovieDescriptionActivity
import com.example.movetime.viewmodel.MovieViewModel
import com.example.movetime.viewmodel.MovieViewModelFactory
import com.example.movetime.viewmodel.adapter.MovieAdapter


class MovieScreen : Fragment() {

    private var _binding: FragmentMovieCreenBinding? = null
    private val binding: FragmentMovieCreenBinding
        get() = _binding ?: throw RuntimeException(
            "FragmentMovieCreenBinding Fragment null"
        )
    val adapter = MovieAdapter()
    private var selectedFilterIndex: Int? = null

    private lateinit var viewModel: MovieViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieCreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = MovieViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        setUpFilters()
        setUpRecyclerView()
        observableEvents()
    }

    private fun observableEvents() {
        viewModel.movieList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setUpFilters() {
        val filters = listOf("Комедия", "Боевик", "Драмма", "Ужасы","Триллер","Мультфильм")
        for (i in filters.indices) { // Используем индексы для отслеживания выбранного фильтра
            val filterView = LayoutInflater.from(requireContext())
                .inflate(R.layout.filter_item, binding.filterContainer, false)
            val tv = filterView.findViewById<TextView>(R.id.tvFilter)
            tv.text = filters[i]

            filterView.setOnClickListener {
                // Проверка, был ли уже выбран этот фильтр
                if (selectedFilterIndex == i) {
                    filterView.setBackgroundColor(Color.TRANSPARENT) // Сброс цвета фона
                    tv.setTextColor(Color.BLACK) // Сброс цвета текста
                    selectedFilterIndex = null // Сброс выбранного индекса
                    binding.apply {
                        notFilmByFilter.visibility = View.GONE
                        tvNot.visibility = View.GONE
                    }
                    viewModel.movieList.observe(viewLifecycleOwner) {
                        adapter.submitList(it)
                    }
                } else {
                    // Сброс стиля предыдущего выбранного фильтра
                    selectedFilterIndex?.let { index ->
                        val previousFilterView = binding.filterContainer.getChildAt(index)
                        previousFilterView.setBackgroundColor(Color.TRANSPARENT) // Сброс цвета фона
                        val previousTextView = previousFilterView.findViewById<TextView>(R.id.tvFilter)
                        previousTextView.setTextColor(Color.BLACK) // Сброс цвета текста
                    }

                    // Установка нового выбранного фильтра
                    selectedFilterIndex = i
                    filterView.setBackgroundColor(Color.LTGRAY) // Изменение цвета фона для выбранного фильтра
                    tv.setTextColor(Color.WHITE) // Изменение цвета текста для выбранного фильтра

                    // Получение фильмов по выбранному типу
                    viewModel.getMovieByType(filters[i]).observe(viewLifecycleOwner) {
                        if (it.isEmpty()) {
                            binding.apply {
                                notFilmByFilter.visibility = View.VISIBLE
                                tvNot.visibility = View.VISIBLE
                            }
                        }
                        adapter.submitList(it)
                    }
                }
            }

            binding.filterContainer.addView(filterView)
        }
    }

    private fun setUpRecyclerView() {
        binding.movieList.layoutManager = LinearLayoutManager(requireContext())
        adapter.apply {
            onItemClickListener = {
                val currentItem = it
                val intent = Intent(requireContext(), MovieDescriptionActivity::class.java)
                intent.putExtra("EXTRA_USER", currentItem)
                startActivity(intent)
            }

        }
        binding.movieList.adapter = adapter

    }

    companion object {

        fun newInstance() =
            MovieScreen()
    }
}