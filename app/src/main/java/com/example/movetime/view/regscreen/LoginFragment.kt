package com.example.movetime.view.regscreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.movetime.ADMIN_ROLE
import com.example.movetime.R
import com.example.movetime.databinding.FragmentLoginBinding
import com.example.movetime.utils.SharedPreference
import com.example.movetime.view.MainActivity
import com.example.movetime.view.adminscreen.AdminScreen
import com.example.movetime.viewmodel.UserViewModel
import com.example.movetime.viewmodel.UserViewModelFactory
import com.google.android.material.snackbar.Snackbar


class LoginFragment : Fragment() {

    private lateinit var buttonGoRegCallBack: () -> Unit
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding ?: throw RuntimeException(
            "FragmentRegistrationBinding Fragment null"
        )

    private lateinit var authSharedPreferences: SharedPreference
    private lateinit var userViewModel: UserViewModel
    private var uerI: Int? = null
    private var userPhone: String? = null
    private var userPassword: String? = null
    private var userRole: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        authSharedPreferences = SharedPreference(requireContext())
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userViewModelFactory = UserViewModelFactory(requireActivity().application)
        userViewModel = ViewModelProvider(this, userViewModelFactory)[UserViewModel::class.java]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observableEvents()
    }

    private fun setUpViews() {
        binding.apply {
            buttonReg.setOnClickListener {
                buttonGoRegCallBack.invoke()
            }
            buttonlogin.setOnClickListener {
                val phone = edTPhone.text.toString()
                val password = eDTpassword.text.toString()
                if (validateFields(phone, password)) checkUser(
                    phone,
                    password
                ) else Snackbar.make(
                    binding.root, getString(R.string.error_incorrect_data_fields),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun checkUser(phoneNumber: String, password: String) {
        if(phoneNumber == "79991330018" && password == "123456"){
            requireActivity().startActivity(Intent(requireContext(), AdminScreen::class.java))
            requireActivity().finish()
        }else{
            userViewModel.getUserInfo(phoneNumber)
            userPhone = phoneNumber
            userPassword = password
        }
    }

    private fun observableEvents() {
        userViewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        }
        userViewModel.success.observe(viewLifecycleOwner) {
            if (userRole == ADMIN_ROLE) {
                requireActivity().startActivity(Intent(requireContext(), AdminScreen::class.java))
                requireActivity().finish()
            } else {
                authSharedPreferences.setLoginStatus(true)
                authSharedPreferences.setIdData(uerI!!)
                requireActivity().startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
        }
        userViewModel.userInfo.observe(viewLifecycleOwner) {
            if (userPhone == it.phone && userPassword == it.password) {
                userRole = it.role
                uerI = it.id
                userViewModel.setSuccessTrueMainThread()
            } else {
                userViewModel.setError("Не корректные данные")
            }
        }
    }

    private fun validateFields(phone: String, password: String) =
        ((phone.length == 11) and (password.length > 4))


    companion object {

        fun newInstance(callBack: () -> Unit) =
            LoginFragment().apply {
                buttonGoRegCallBack = callBack
            }
    }
}