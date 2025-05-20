package com.example.movetime.view.regscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movetime.R
import com.example.movetime.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {


    private lateinit var callBackButton: () -> Unit
    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding
        get() = _binding ?: throw RuntimeException(
            "Shop Item Fragment null"
        )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonNext.setOnClickListener {
            callBackButton.invoke()
        }
    }

    companion object {
        fun newInstance(callBack: () -> Unit) = WelcomeFragment().apply {
            callBackButton = callBack
        }
    }
}