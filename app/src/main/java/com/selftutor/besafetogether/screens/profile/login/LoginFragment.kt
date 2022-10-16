package com.selftutor.besafetogether.screens.profile.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.data.api.response.BaseResponse
import com.selftutor.besafetogether.data.api.response.LoginResponse
import com.selftutor.besafetogether.databinding.FragmentLoginBinding
import com.selftutor.besafetogether.screens.BaseFragment
import com.selftutor.besafetogether.screens.factory
import com.selftutor.besafetogether.utils.SessionManager

class LoginFragment: BaseFragment() {

	private lateinit var binding: FragmentLoginBinding
	private val viewModel : LoginViewModel by viewModels { factory() }


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentLoginBinding.inflate(inflater, container, false)
		binding.viewModel = viewModel

		binding.createAccountTextView.setOnClickListener {
			findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
		}

		setLoginButtonObserver()
		setFieldsObserver()

		return binding.root
	}

	private fun setLoginButtonObserver(){
		with(binding) {
			viewModel!!.loginResult.observe(viewLifecycleOwner) {
				when(it){
					is BaseResponse.Error -> {
						showToast(it.msg!!)
						onLoginFinish()
					}
					is BaseResponse.Loading -> {
						onLoginLoading()
					}
					is BaseResponse.Success -> {
						showToast(it.data!!.message)
						onLoginFinish()
						processLogin(it.data)
					}
				}
			}
		}
	}

	private fun setFieldsObserver(){
		with(binding) {
			viewModel!!.actionEmailError.observe(viewLifecycleOwner){
				emailEditText.error = it
			}
			viewModel!!.actionPasswordError.observe(viewLifecycleOwner){
				passwordEditText.error = it
			}
			viewModel!!.loginEnabled.observe(viewLifecycleOwner){
				loginButton.isEnabled = it
			}
		}
	}

	private fun processLogin(data: LoginResponse?){
		if (!data?.token.isNullOrEmpty()) {
			data?.token?.let { SessionManager.saveAuthToken(requireContext(), it) }
			findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
		}
	}

	private fun onLoginFinish(){
		binding.progressBar.visibility = View.GONE
		binding.loginButton.isEnabled = true
	}

	private fun onLoginLoading(){
		binding.progressBar.visibility = View.VISIBLE
		binding.loginButton.isEnabled = false
	}
}