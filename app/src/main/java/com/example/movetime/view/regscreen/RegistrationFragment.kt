package com.example.movetime.view.regscreen

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.movetime.R
import com.example.movetime.USER_ROLE
import com.example.movetime.databinding.FragmentRegistrationBinding
import com.example.movetime.databinding.FragmentWelcomeBinding
import com.example.movetime.utils.File
import com.example.movetime.utils.SharedPreference
import com.example.movetime.view.MainActivity
import com.example.movetime.viewmodel.UserViewModel
import com.example.movetime.viewmodel.UserViewModelFactory
import com.example.movetime.viewmodel.model.User
import com.google.android.material.snackbar.Snackbar


class RegistrationFragment : Fragment() {


    private var _binding: FragmentRegistrationBinding? = null
    private val binding: FragmentRegistrationBinding
        get() = _binding ?: throw RuntimeException(
            "FragmentRegistrationBinding Fragment null"
        )

    private lateinit var authSharedPreferences: SharedPreference
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authSharedPreferences = SharedPreference(requireContext())
        val userViewModelFactory = UserViewModelFactory(requireActivity().application)
        userViewModel = ViewModelProvider(this, userViewModelFactory)[UserViewModel::class.java]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observableEvents()
        setUpViews()
        observableEvents()
    }

    private fun setUpViews() {
        binding.apply {
            buttonReg.setOnClickListener {
                val phone = edTPhone.text.toString()
                val passwordFirst = eDTpassword.text.toString()
                val passwordSecond = eDTpasswordRepeat.text.toString()
                val user = User(phone, passwordFirst, USER_ROLE)
                if (validateFields(
                        phone,
                        passwordFirst,
                        passwordSecond
                    )
                ) userViewModel.registerNewUser(user) else Snackbar.make(
                    binding.root, getString(R.string.error_incorrect_data_fields),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun observableEvents() {
        userViewModel.success.observe(viewLifecycleOwner) {
            requireActivity().startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
        userViewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun validateFields(phoneFirst: String, phoneSecond: String, password: String) =
        ((phoneFirst.length == 11) or (phoneSecond == phoneFirst) or (password.length > 6))

    companion object {

        fun newInstance() =
            RegistrationFragment()
    }
}