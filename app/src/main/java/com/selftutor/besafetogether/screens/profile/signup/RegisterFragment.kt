package com.selftutor.besafetogether.screens.profile.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.data.api.response.BaseResponse
import com.selftutor.besafetogether.databinding.FragmentRegisterBinding
import com.selftutor.besafetogether.screens.BaseFragment
import com.selftutor.besafetogether.screens.factory

class RegisterFragment : BaseFragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setFieldsObserver()
        setRegisterObserver()

        binding.loginTextView.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.registerButton.setOnClickListener {
            viewModel.onRegisterUser()
        }

        return binding.root
    }

    private fun setRegisterObserver() {
        with(binding) {
            viewModel!!.registerResult.observe(viewLifecycleOwner) {
                when(it){
                    is BaseResponse.Error -> {
                        showToast(it.msg!!)
                        progressBar.visibility = View.GONE
                        registerButton.isEnabled = true
                    }
                    is BaseResponse.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        registerButton.isEnabled = false
                    }
                    is BaseResponse.Success -> {
                        showToast("User registered")
                        progressBar.visibility = View.GONE
                        registerButton.isEnabled = true
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                }
            }
        }
    }

    private fun setFieldsObserver() {
        with(binding) {
            viewModel!!.actionUsernameError.observe(viewLifecycleOwner) {
                usernameEditText.error = it
            }
            viewModel!!.actionEmailError.observe(viewLifecycleOwner) {
                emailEditText.error = it
            }
            viewModel!!.actionPhoneError.observe(viewLifecycleOwner) {
                phoneEditText.error = it
            }
            viewModel!!.actionPasswordError.observe(viewLifecycleOwner) {
                passwordEditText.error = it
            }
            viewModel!!.actionConfirmPasswordError.observe(viewLifecycleOwner) {
                confirmPasswordEditText.error = it
            }
            viewModel!!.registerEnabled.observe(viewLifecycleOwner) {
                registerButton.isEnabled = it
            }
        }
    }
}
